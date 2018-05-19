package poi.giftiacoder.civil_mod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import poi.giftiacoder.civil_mod.building.ModBuildings;

public class TileEntityChunkMaster extends TileEntity {
	
	public static enum ChunkType {
		WASTE, BARBARIAN, CIVILIAN, LORD, KING;
	}
	public static enum ChunkState {
		BUILD, NORMAL, BREAK, RUIN;
	}
	
	private int capitalX = 0, capitalZ = 0;
	private ChunkType chunkType = null;
	private ChunkState chunkState = ChunkState.NORMAL;
	
	public TileEntityChunkMaster() {}
	
	public ChunkType getChunkType() {
		return chunkType;
	}
	public void setChunkType(ChunkType type) {
		this.chunkType = type;
	}
	public ChunkState getChunkState() {
		return chunkState;
	}
	public void setChunkState(ChunkState chunkState) {
		this.chunkState = chunkState;
	}
	
	public TileEntityChunkMaster getCapital() {
		if (chunkType != ChunkType.KING) {
			TileEntity tileEntity = this.world.getTileEntity(new BlockPos(capitalX, 0, capitalZ));
			if (tileEntity != null && tileEntity instanceof TileEntityChunkMaster) {
				return (TileEntityChunkMaster)tileEntity;
			}
		}
		return null;
	}
	void setCapital(TileEntityChunkMaster master) {
		BlockPos pos = master.getPos();
		capitalX = pos.getX();
		capitalZ = pos.getZ();
	}
	
	public void generateChunk() {
		// TODO
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return super.writeToNBT(compound);
	}
	
	public static TileEntityChunkMaster getChunkMaser(World world, int chunkX, int chunkZ) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos((chunkX << 4) + 1, 0, (chunkZ << 4) + 1));
		if (tileEntity != null && tileEntity instanceof TileEntityChunkMaster) {
			return (TileEntityChunkMaster)tileEntity;
		}
		return null;
	}
	
}
