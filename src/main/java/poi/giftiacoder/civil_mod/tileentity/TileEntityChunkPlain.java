package poi.giftiacoder.civil_mod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import poi.giftiacoder.civil_mod.world.civil.DimensionCivil;

public class TileEntityChunkPlain extends TileEntityChunkData {
	
	public static enum BuildingType {
		WASTELAND, SUBURBAN, CASTLE;
	};
	
	public BuildingType buildingType;
	public int capitalX = Integer.MIN_VALUE, capitalZ = Integer.MIN_VALUE;
	
	public TileEntityChunkPlain() {}
	
	public TileEntityChunkPlain(World world, BuildingType buildingType, float productivity, int chunkX, int chunkZ) {
		super(world, ChunkType.PLAIN, productivity, chunkX, chunkZ);
		this.buildingType = buildingType;
	}
	
	public void setTerritoryOf(TileEntityChunkCastle capital) {
		buildingType = BuildingType.SUBURBAN;
		capitalX = capital.chunkX;
		capitalZ = capital.chunkZ;
		
		int xBase = chunkX << 4, zBase = chunkZ << 4;
		
		for (int x = 1; x < 15; ++x) {
			for (int z = 1; z < 15; ++z) {
				world.setBlockState(new BlockPos(xBase + x,  DimensionCivil.CIVIL_WORLD_LOW_LEVEL, zBase + z), 
						capital.campColorBlock);
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("buildingType")) {
			buildingType = BuildingType.values()[nbt.getInteger("buildingType")];
		}
		if (nbt.hasKey("capitalX")) {
			capitalX = nbt.getInteger("capitalX");
		}
		if (nbt.hasKey("capitalZ")) {
			capitalZ = nbt.getInteger("capitalZ");
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("buildingType", buildingType.ordinal());
		nbt.setInteger("capitalX", capitalX);
		nbt.setInteger("capitalZ", capitalZ);
		return super.writeToNBT(nbt);
	}
}
