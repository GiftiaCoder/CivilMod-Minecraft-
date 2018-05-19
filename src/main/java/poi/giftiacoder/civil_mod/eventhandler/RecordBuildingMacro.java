package poi.giftiacoder.civil_mod.eventhandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import poi.giftiacoder.civil_mod.ModBlocks;

public class RecordBuildingMacro extends HashMap<IBlockState, Set<BlockPos>> {
	private String currentBuildingName = null;
	private int x, y, z;
	
	@SubscribeEvent
	public void recordBuildingCommand(ServerChatEvent event) throws IOException {
		String msg = event.getMessage();
		if (msg.startsWith("record")) {
			String[] tokens = msg.split(" +");
			if (tokens.length > 1) {
				event.getPlayer().sendMessage(new TextComponentString("begin record : " + (currentBuildingName = tokens[1])));
				
				clear();
				BlockPos p = event.getPlayer().getPosition();
				x = p.getX();
				y = p.getY();
				z = p.getZ();
				
				for (int i = 0; i < 16; ++i) {
					for (int j = 0; j < 16; ++j) {
						BlockPos pos = new BlockPos(x + i, y, z + j);
						
						event.getPlayer().getServerWorld().setBlockState(pos, Block.getStateById(0x62));
						add(Block.getStateById(0x62), pos);
					}
				}
			}
		}
		else if(msg.equals("save")) {
			if (currentBuildingName == null)
			{
				event.getPlayer().sendMessage(new TextComponentString("record not begin"));
				return;
			}
			event.getPlayer().sendMessage(new TextComponentString("begin save : " + currentBuildingName));
			
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(String.format("building_%s.mdl", currentBuildingName))));
			out.writeInt(size());
			for (Entry<IBlockState, Set<BlockPos>> ent : entrySet()) {
				if (ent.getValue().size() == 0) {
					continue;
				}
				out.writeInt(Block.getIdFromBlock(ent.getKey().getBlock()));
				out.writeInt(Block.getStateId(ent.getKey()));
				out.writeInt(ent.getKey().toString().length());
				out.write(ent.getKey().toString().getBytes());
				
				out.writeInt(ent.getValue().size());
				for (BlockPos pos : ent.getValue()) {
					out.writeInt(pos.getX() - x);
					out.writeInt(pos.getY() - y);
					out.writeInt(pos.getZ() - z);
				}
			}
			out.close();
			currentBuildingName = null;
			clear();
		}
		else if(msg.equals("drop")) {
			event.getPlayer().sendMessage(new TextComponentString("clear"));
			currentBuildingName = null;
			clear();
		}
		else if(msg.startsWith("load")) {
			String[] tokens = msg.split(" +");
			if (tokens.length > 1) {
				clear();
				
				BlockPos pos = event.getPlayer().getPosition();
				x = pos.getX();
				y = pos.getY();
				z = pos.getZ();
				
				event.getPlayer().sendMessage(new TextComponentString("begin load : " + (currentBuildingName = tokens[1])));
				DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(String.format("building_%s.mdl", currentBuildingName))));
				int stateNum = in.readInt();
				for (int stateIdx = 0; stateIdx < stateNum; ++stateNum) {
					int blockId = in.readInt();
					int blockState = in.readInt();
					int stateNameLen = in.readInt();
					byte[] nameBytes = new byte[stateNameLen];
					in.read(nameBytes);
					String stateName = new String(nameBytes);
					
					event.getPlayer().sendMessage(new TextComponentString("load block state : " + stateName));
					
					IBlockState state = Block.getStateById(blockState);
					
					int posNum = in.readInt();
					for (int posIdx = 0; posIdx < posNum; ++posIdx) {
						BlockPos blockPos = new BlockPos(in.readInt() + x, in.readInt() + y, in.readInt() + z);
						add(state, blockPos);
						event.getPlayer().getServerWorld().setBlockState(blockPos, state);
					}
				}
				in.close();
				
				if (tokens.length > 2) {
					String direction = tokens[2];
					Vec3d playerVec = event.getPlayer().getPositionVector();
					if (direction.equals("x")) {
						event.getPlayer().setPositionAndUpdate(playerVec.x + 16, playerVec.y, playerVec.z);
					}
					else if (direction.equals("z")) {
						event.getPlayer().setPositionAndUpdate(playerVec.x, playerVec.y, playerVec.z + 16);
					}
				}
			}
		}
		else if (msg.startsWith("travel")) {
			String[] tokens = msg.split(" +");
			if (tokens.length > 1) {
				try {
					int id = Integer.parseInt(tokens[1]);
					System.out.println(id);
					event.getPlayer().changeDimension(id);
				} catch(Exception e) {}
			}
		}
	}
	@SubscribeEvent
	public void recordBuilding(BlockEvent event) {
		if (currentBuildingName == null) {
			return;
		}
		
		if (event instanceof BlockEvent.PlaceEvent) {
			BlockEvent.PlaceEvent placeEvent = (BlockEvent.PlaceEvent)event;
			remove(placeEvent.getPlacedAgainst(), event.getPos());
			add(event.getState(), event.getPos());
		}
		else if (event instanceof BlockEvent.BreakEvent) {
			remove(event.getState(), event.getPos());
		}
	}
	
	@SubscribeEvent
	public void listenPlayerRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
		//if (currentBuildingName == null) {
		//	return;
		//}
		
		BlockPos pos = getBlockPos(event.getPos(), event.getFace());
		if (event.getItemStack().getItem() == Items.WATER_BUCKET) {
			System.out.println(event.getPhase());
			//System.out.println(event.getResult());
		}
	}
	
	private void add(IBlockState state, BlockPos pos) {
		System.out.println("add: " + state + ":" + pos);
		Set<BlockPos> set = get(state);
		if (set == null) {
			put(state, set = new HashSet<>());
		}
		set.add(pos);
	}
	
	private void remove(IBlockState state, BlockPos pos) {
		Set<BlockPos> set = get(state);
		if (set != null) {
			System.out.println("remove: " + state + ":" + pos);
			set.remove(pos);
		}
	}
	
	private BlockPos getBlockPos(BlockPos pos, EnumFacing face) {
		switch (face) {
		case DOWN:
			return pos.down();
		case UP:
			return pos.up();
		case EAST:
			return pos.east();
		case NORTH:
			return pos.north();
		case SOUTH:
			return pos.south();
		case WEST:
			return pos.west();
		}
		return pos;
	}
}
