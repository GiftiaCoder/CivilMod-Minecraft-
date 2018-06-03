package poi.giftiacoder.civil_mod.entity.civil.human.ai;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import poi.giftiacoder.civil_mod.entity.civil.demon.EntityDemonBase;
import poi.giftiacoder.civil_mod.entity.civil.human.EntityHumanBase;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkPlain;
import poi.giftiacoder.civil_mod.util.ChunkDataUtil;
import poi.giftiacoder.civil_mod.util.ChunkDataUtil.IChunkFilter;
import poi.giftiacoder.civil_mod.util.ChunkDataUtil.IChunkValue;
import poi.giftiacoder.civil_mod.util.EntityUtil;

public class HumanAiTravller implements IHumanAi {
	
	private EntityHumanBase human;
	private float encourage = 0.0F;
	
	private boolean isAnneal = false;
	private boolean isInDanger = false;
	
	private IChunkValue valueToTerritory = new IChunkValue() {
		@Override
		public float value(TileEntityChunkData curr, TileEntityChunkData next) {
			if (curr instanceof TileEntityChunkPlain) {
				TileEntityChunkPlain plain = (TileEntityChunkPlain) curr;
				if (plain.buildingType != TileEntityChunkPlain.BuildingType.WASTELAND) {
					return Float.MIN_VALUE;
				}
			}
			if (curr.productivity < next.productivity) {
				return next.productivity;
			}
			return Float.MIN_VALUE;
		}
	};
	private IChunkFilter filterToAnneal = new IChunkFilter() {
		@Override
		public boolean match(TileEntityChunkData curr, TileEntityChunkData next) {
			if (curr.productivity > next.productivity) {
				return getEncourageForChunk(next) <= encourage;
			}
			return false;
		}
	};
	
	public HumanAiTravller(EntityHumanBase human) {
		this.human = human;
	}
	
	@Override
	public void updateAi() {
		float tx = 0, tz = 0;
		
		TileEntityChunkData nextChunkData = null;
		if (isAnneal) {
			nextChunkData = ChunkDataUtil.getRandChunk(human.world, ((int) human.posX) >> 4, ((int) human.posZ) >> 4, 1, filterToAnneal);
		}
		else {
			nextChunkData = ChunkDataUtil.getMaximizedChunk(human.world, ((int) human.posX) >> 4, ((int) human.posZ) >> 4, 1, valueToTerritory);
		}
		
		if (nextChunkData == null) {
			if (isCurrentChunkTerritory()) {
				// TODO set as civilian
			}
			else {
				isAnneal = ! isAnneal;
				
				human.setCustomNameTag((isAnneal ? "anneal : " : "search : ") + encourage);
				System.out.printf("exchange: (%f, %f)->(%d, %d)\n", human.posX, human.posZ, ((int) human.posX) >> 4, ((int) human.posZ) >> 4);
			}
		}
		else {
			float x = (float) (((nextChunkData.chunkX << 4) | 8) - human.posX);
			float z = (float) (((nextChunkData.chunkZ << 4) | 8) - human.posZ);
			float attraction = ChunkDataUtil.getAttractionToTraveller(human, nextChunkData);
			tx += x * attraction;
			tz += z * attraction;
		}
		
		// get vector to keep away from danger 
		boolean inDanger = false;
		for (Entity entity : human.entityInSight) {
			if (entity instanceof EntityDemonBase) {
				float dang = EntityUtil.getDangrousFromEntity(human, entity, 1.0F);
				tx += dang * (human.posX - entity.posX);
				tz += dang * (human.posZ - entity.posZ);
				
				inDanger = true;
			}
		}
		
		isInDanger = inDanger;
		
		if (human.getNavigator().noPath() || human.getNavigator().getPath().isFinished() || 
				human.world.rand.nextInt(4) == 0 || 
				isInDanger) {
			
			float d = MathHelper.sqrt(tx * tx + tz * tz);
			human.getNavigator().tryMoveToXYZ(
					human.posX + (tx * 8.0F) / d, 
					human.posY + (isAnneal ? 5.0F : -1.0F), 
					human.posZ + (tz * 8.0F) / d, 
					isInDanger ? 0.65F : 0.45F);
		}
	}

	@Override
	public void onEnterChunk(int oldChunkX, int oldChunkZ, int newChunkX, int newChunkZ) {
		if (isAnneal) {
			encourage -= getEncourageForChunk(TileEntityChunkData.getChunkData(human.world, newChunkX, newChunkZ));
		}
		else {
			encourage += getEncourageFromChunk(TileEntityChunkData.getChunkData(human.world, newChunkX, newChunkZ));
		}
		human.setCustomNameTag((isAnneal ? "anneal : " : "search : ") + encourage);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isCurrentChunkTerritory() {
		// TODO
		return false;
	}
	
	private static float getEncourageFromChunk(TileEntityChunkData data) {
		float v = data.productivity - 100.0F;
		if (v >= 50.0F) {
			v = 1.0F;
		}
		else if (v <= 0.0F) {
			v = 0.0F;
		}
		else {
			v /= 50.0F;
		}
		return v * 1.5F;
	}
	
	private static float getEncourageForChunk(TileEntityChunkData data) {
		float v = 150.0F - data.productivity;
		if (v >= 50.0F) {
			return 1.0F;
		}
		else if (v <= 0.0F) {
			return 0.0F;
		}
		return v / 50.0F;
	}
	
}
