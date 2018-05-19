package poi.giftiacoder.civil_mod.commands;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import poi.giftiacoder.civil_mod.R;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkMaster;

public class CmdCheckChunkMaster extends CommandBase {

	private static final List<String> ALIASES = Lists.newArrayList(R.MODID, "chkcm");
	
	@Override
	public String getName() {
		return "chkcm";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "chkcm";
	}
	
	@Override
	public List<String> getAliases() {
		return ALIASES;
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		BlockPos pos = sender.getPosition();
		pos = new BlockPos((pos.getX() & 0xfffffff0) + 1, 0, (pos.getZ() & 0xfffffff0) + 1);
		System.out.println(pos);
		
		TileEntity tileEntity = sender.getEntityWorld().getTileEntity(pos);
		
		if (tileEntity != null && tileEntity instanceof TileEntityChunkMaster) {
			//sender.sendMessage(new TextComponentString(String.format("%sChunkMaster age: %d, pos: %s", TextFormatting.RED, ((TileEntityChunkMaster) tileEntity).getAge(), tileEntity.getPos())));
		}
		else {
			sender.sendMessage(new TextComponentString(String.format("%sCannot get chunk master, block state: %s, pos: %s, tileentity: %s", 
					TextFormatting.RED, sender.getEntityWorld().getBlockState(pos), pos, sender.getEntityWorld().getTileEntity(pos))));
		}
	}

}
