package poi.giftiacoder.civil_mod.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import poi.giftiacoder.civil_mod.entity.civil.human.EntityHumanBase;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;

public class ChunkDataUtil {

	public static interface IChunkFilter {
		public boolean match(TileEntityChunkData curr, TileEntityChunkData next);
	}
	
	public static TileEntityChunkData getRandChunk(World world, int chunkX, int chunkZ, int chunkRange, IChunkFilter filter) {
		TileEntityChunkData currChunk = TileEntityChunkData.getChunkData(world, chunkX, chunkZ);
		
		int edge = (chunkRange << 1) + 1;
		
		TileEntityChunkData[] dataList = new TileEntityChunkData[edge * edge];
		int dataNum = 0;
		
		for (int i = -chunkRange; i <= chunkRange; ++i) {
			for (int j = -chunkRange; j <= chunkRange; ++j) {
				if (i != 0 && j != 0) {
					TileEntityChunkData data = TileEntityChunkData.getChunkData(world, chunkX + i, chunkZ + j);
					if (data != null && filter.match(currChunk, data)) {
						dataList[dataNum] = data;
						++dataNum;
					}
				}
			}
		}
		
		if (dataNum > 0) {
			return dataList[world.rand.nextInt(dataNum)];
		}
		return null;
	}
	
	public static interface IChunkValue {
		public float value(TileEntityChunkData curr, TileEntityChunkData next);
	}
	
	public static TileEntityChunkData getMaximizedChunk(World world, int chunkX, int chunkZ, int chunkRange, IChunkValue value) {
		TileEntityChunkData currChunk = TileEntityChunkData.getChunkData(world, chunkX, chunkZ);
		
		TileEntityChunkData maxChunk = null;
		float maxValue = Float.MIN_VALUE;
		
		for (int i = -chunkRange; i <= chunkRange; ++i) {
			for (int j = -chunkRange; j <= chunkRange; ++j) {
				if (i != 0 && j != 0) {
					TileEntityChunkData data = TileEntityChunkData.getChunkData(world, chunkX + i, chunkZ + j);
					if (data != null) {
						float curValue = value.value(currChunk, data);
						if (curValue > maxValue) {
							maxChunk = data;
							maxValue = curValue;
						}
					}
				}
			}
		}
		
		return maxChunk;
	}
	
	public static float getDistanceSqFromEntity(TileEntityChunkData chunkData, Entity entity) {
		float x = (chunkData.chunkX << 4) | 8;
		float z = (chunkData.chunkZ << 4) | 8;
		return x * x + z * z;
	}
	
	public static float getAttractionFromChunk(TileEntityChunkData chunkData) {
		float a = chunkData.productivity - 100.0F;
		if (a >= 50.0F) {
			return 1.0F;
		}
		else if (a <= 0.0F) {
			return 0.0F;
		}
		return a / 50.0F;
	}
	
	public static float getAttractionToTraveller(EntityHumanBase traveller, TileEntityChunkData chunkData) {
		return getAttractionFromChunk(chunkData) / (getDistanceSqFromEntity(chunkData, traveller) + 1.0F);
	}
	
}
