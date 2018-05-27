package poi.giftiacoder.civil_mod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;
import poi.giftiacoder.civil_mod.ModBlocks;
import poi.giftiacoder.civil_mod.building.ModBuildings;
import poi.giftiacoder.civil_mod.civilmaterial.Resources;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkPlain.BuildingType;
import poi.giftiacoder.civil_mod.world.civil.DimensionCivil;

public class TileEntityChunkData extends TileEntity {
	
	public static enum ChunkType {
		PLAIN {
			@Override
			public TileEntityChunkData createChunkData(World world, float productivity, int chunkX, int chunkZ) {
				return new TileEntityChunkPlain(world, BuildingType.WASTELAND, productivity, chunkX, chunkZ);
			}
		}, MOUNTAIN, PLATEAU;
		
		public static ChunkType getChunkType(int lowHeightCount, int topLevelCount) {
			if (lowHeightCount == 256) {
				return PLAIN;
			}
			else if (topLevelCount != 256) {
				return ChunkType.MOUNTAIN;
			}
			return ChunkType.PLATEAU;
		}
		
		public TileEntityChunkData createChunkData(World world, float productivity, int chunkX, int chunkZ) {
			return new TileEntityChunkData(world, this, productivity, chunkX, chunkZ);
		}
	};
	
	public float productivity = 0;
	public ChunkType chunkType = ChunkType.PLAIN;
	
	public int chunkX = Integer.MIN_VALUE, chunkZ = Integer.MIN_VALUE;
	public float[] resourcesBasicAmount = new float[Resources.values().length];
	
	private int productivityGreaterThanNeighbourCount = 0;
	
	public TileEntityChunkData() {}
	
	public TileEntityChunkData(World world, ChunkType chunkType, float productivity, int chunkX, int chunkZ) {
		
		this.chunkType = chunkType;
		this.productivity = productivity;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		
		for (int i = 0; i < resourcesBasicAmount.length; ++i) {
			resourcesBasicAmount[i] = Resources.values()[i].getWeight(chunkX, chunkZ);
		}
	}
	
	public TileEntityChunkData getRandLowerProductivityChunkNearby() {
		int n = 0;
		TileEntityChunkData[] lowerList = new TileEntityChunkData[4];
		TileEntityChunkData cd = null;
		
		cd = getChunkData(world, chunkX - 1, chunkZ);
		if (cd != null && productivity > cd.productivity) {
			lowerList[n] = cd;
			++n;
		}
		cd = getChunkData(world, chunkX + 1, chunkZ);
		if (cd != null && productivity > cd.productivity) {
			lowerList[n] = cd;
			++n;
		}
		cd = getChunkData(world, chunkX, chunkZ - 1);
		if (cd != null && productivity > cd.productivity) {
			lowerList[n] = cd;
			++n;
		}
		cd = getChunkData(world, chunkX, chunkZ + 1);
		if (cd != null && productivity > cd.productivity) {
			lowerList[n] = cd;
			++n;
		}
		
		if (n > 0) {
			return lowerList[world.rand.nextInt(n)];
		}
		return null;
	}
	
	public TileEntityChunkData getRandHigherProductivityChunkNearby() {
		int n = 0;
		TileEntityChunkData[] higherList = new TileEntityChunkData[4];
		TileEntityChunkData cd = null;
		
		cd = getChunkData(world, chunkX - 1, chunkZ);
		if (cd != null && productivity < cd.productivity) {
			higherList[n] = cd;
			++n;
		}
		cd = getChunkData(world, chunkX + 1, chunkZ);
		if (cd != null && productivity < cd.productivity) {
			higherList[n] = cd;
			++n;
		}
		cd = getChunkData(world, chunkX, chunkZ - 1);
		if (cd != null && productivity < cd.productivity) {
			higherList[n] = cd;
			++n;
		}
		cd = getChunkData(world, chunkX, chunkZ + 1);
		if (cd != null && productivity < cd.productivity) {
			higherList[n] = cd;
			++n;
		}
		
		if (n > 0) {
			return higherList[world.rand.nextInt(n)];
		}
		return null;
	}
	
	public TileEntityChunkData getMinProducvivityChunkNearby(boolean lowerThanThis) {
		float mp = lowerThanThis ? productivity : Float.MAX_VALUE;
		TileEntityChunkData cd = null, target = null;
		
		cd = getChunkData(world, chunkX + 1, chunkZ);
		if (cd != null && mp > cd.productivity) {
			mp = cd.productivity;
			target = cd;
		}
		cd = getChunkData(world, chunkX - 1, chunkZ);
		if (cd != null && mp > cd.productivity) {
			mp = cd.productivity;
			target = cd;
		}
		cd = getChunkData(world, chunkX, chunkZ + 1);
		if (cd != null && mp > cd.productivity) {
			mp = cd.productivity;
			target = cd;
		}
		cd = getChunkData(world, chunkX, chunkZ - 1);
		if (cd != null && mp > cd.productivity) {
			mp = cd.productivity;
			target = cd;
		}
		
		return target;
	}
	
	public TileEntityChunkData getMaxProductivityChunkNearby(boolean higherThanThis) {
		float mp = higherThanThis ? productivity : 0.0F;
		TileEntityChunkData cd = null, target = null;
		
		cd = getChunkData(world, chunkX + 1, chunkZ);
		if (cd != null && mp < cd.productivity) {
			mp = cd.productivity;
			target = cd;
		}
		cd = getChunkData(world, chunkX - 1, chunkZ);
		if (cd != null && mp < cd.productivity) {
			mp = cd.productivity;
			target = cd;
		}
		cd = getChunkData(world, chunkX, chunkZ + 1);
		if (cd != null && mp < cd.productivity) {
			mp = cd.productivity;
			target = cd;
		}
		cd = getChunkData(world, chunkX, chunkZ - 1);
		if (cd != null && mp < cd.productivity) {
			mp = cd.productivity;
			target = cd;
		}
		
		return target;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		if (nbt.hasKey("productivity")) {
			productivity = nbt.getFloat("productivity");
		}
		if (nbt.hasKey("chunkType")) {
			chunkType = ChunkType.values()[nbt.getInteger("chunkType")];
		}
		if (nbt.hasKey("chunkX")) {
			chunkX = nbt.getInteger("chunkX");
		}
		if (nbt.hasKey("chunkZ")) {
			chunkZ = nbt.getInteger("chunkZ");
		}
		
		Resources.readFromNBT(this, nbt);
		
		if (nbt.hasKey("productivityGreaterThanNeighbourCount")) {
			productivityGreaterThanNeighbourCount = nbt.getInteger("productivityGreaterThanNeighbourCount");
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("productivity", productivity);
		nbt.setInteger("chunkType", chunkType.ordinal());
		nbt.setInteger("chunkX", chunkX);
		nbt.setInteger("chunkZ", chunkZ);
		
		Resources.writeToNBT(this, nbt);
		
		nbt.setInteger("productivityGreaterThanNeighbourCount", productivityGreaterThanNeighbourCount);
		
		return super.writeToNBT(nbt);
	}
	
	/**
	 * bind tile entity with chunk
	 */
	public void bindChunkDataWithChunk(Chunk chunk) {
		chunk.addTileEntity(getChunkDataPos(chunkX, chunkZ), this);
		tryResetToCastle();
	}
	
	private void tryResetToCastle() {
		if (chunkType != ChunkType.PLAIN || productivity < DimensionCivil.MIN_CASTLE_CHUNK_PRODUCTIVITY) {
			return;
		}
		
		productivityGreaterThanNeighbourCount += checkNeighbourProductivity(chunkX + 1, chunkZ, productivity);
		productivityGreaterThanNeighbourCount += checkNeighbourProductivity(chunkX - 1, chunkZ, productivity);
		productivityGreaterThanNeighbourCount += checkNeighbourProductivity(chunkX, chunkZ + 1, productivity);
		productivityGreaterThanNeighbourCount += checkNeighbourProductivity(chunkX, chunkZ - 1, productivity);
		
		productivityGreaterThanNeighbourCount += checkNeighbourProductivity(chunkX + 1, chunkZ + 1, productivity);
		productivityGreaterThanNeighbourCount += checkNeighbourProductivity(chunkX + 1, chunkZ - 1, productivity);
		productivityGreaterThanNeighbourCount += checkNeighbourProductivity(chunkX - 1, chunkZ + 1, productivity);
		productivityGreaterThanNeighbourCount += checkNeighbourProductivity(chunkX - 1, chunkZ - 1, productivity);
		
		if (productivityGreaterThanNeighbourCount == 8) {
			setChunkAsCastle();
		}
	}
	
	private int checkNeighbourProductivity(int x, int z, float p) {
		if (world.isChunkGeneratedAt(x, z)) {
			TileEntityChunkData chunkData = TileEntityChunkData.getChunkData(world, x, z);
			if (chunkData != null) {
				if (chunkData.productivity > productivity && chunkData.chunkType == ChunkType.PLAIN) {
					// TODO if chunk type is plain and building type not wasteland, ...
					if (++chunkData.productivityGreaterThanNeighbourCount == 8) {
						chunkData.setChunkAsCastle();
					}
					return 0; // chunk is a plain, and productivity upper than current
				}
				else {
					return 1;
				}
			}
			return 1; // for some reason unknown, ChunkData not generated, just ignore this failure
		}
		return 0; // chunk not generated, suspend temporary
	}
	
	private void setChunkAsCastle() {
		if (world.rand.nextInt(1) == 0) {
			TileEntityChunkCastle castle = new TileEntityChunkCastle(world, productivity, chunkX, chunkZ);
			Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
			chunk.addTileEntity(getChunkDataPos(chunkX, chunkZ), castle);
			world.addTileEntity(castle);
			
			ModBuildings.HOUSE.generate(chunk, DimensionCivil.CIVIL_WORLD_LOW_LEVEL);
		}
	}
	
	public static TileEntityChunkData getChunkData(World world, int chunkX, int chunkZ) {
		if (world.isChunkGeneratedAt(chunkX, chunkZ)) {
			TileEntity tileEntity = world.getChunkFromChunkCoords(chunkX, chunkZ).getTileEntity(getChunkDataPos(chunkX, chunkZ), EnumCreateEntityType.IMMEDIATE);//.getTileEntity(getChunkDataPos(chunkX, chunkZ));
			if (tileEntity != null && tileEntity instanceof TileEntityChunkData) {
				return (TileEntityChunkData)tileEntity;
			}
		}
		return null;
	}
	
	public static BlockPos getChunkDataPosInChunk() {
		return new BlockPos(1, 0, 1);
	}
	
	public static BlockPos getChunkDataPos(int chunkX, int chunkZ) {
		return new BlockPos((chunkX << 4) + 1, 0, (chunkZ << 4) + 1);
	}
	
	public static void perbindChunkDataWithChunkPrimer(ChunkPrimer chunkPrimer) {
		chunkPrimer.setBlockState(1, 0, 1, ModBlocks.CHUNK_DATA.getDefaultState());
	}
}
