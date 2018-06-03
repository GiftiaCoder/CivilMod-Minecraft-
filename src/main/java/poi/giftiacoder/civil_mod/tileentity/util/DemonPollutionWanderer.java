package poi.giftiacoder.civil_mod.tileentity.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.nbt.NBTTagCompound;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkCastle;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData.ChunkType;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkPlain;

public class DemonPollutionWanderer implements IMessageWanderer {

	private TileEntityChunkCastle startChunk = null;
	
	private float demonPollution = 0;
	private int chunkX, chunkZ;
	
	public DemonPollutionWanderer(TileEntityChunkData data) {
		reset(data);
	}
	
	@Override
	public void wander() {
		TileEntityChunkData next = (TileEntityChunkData.getChunkData(startChunk.getWorld(), chunkX, chunkZ)).getRandLowerProductivityChunkNearby();
		if (next != null) {
			chunkX = next.chunkX;
			chunkZ = next.chunkZ;
			
			if (next.chunkType != ChunkType.PLAIN) {
				next.demonPollution += getDemonBaseFromPlain(next) * demonPollution;
				if (next.demonPollution > 4.0F) {
					
					// spawn demon in world
					Entity entity = new EntityIronGolem(next.getWorld());
					IMessageWanderer.spawnEntity(next, entity);
					
					next.demonPollution -= 4.0F;
				}
			}
			else {
				TileEntityChunkPlain plain = (TileEntityChunkPlain)next;
				if (plain.buildingType.isBuilding()) {
					demonPollution += getDemonPollutionFromPlain(plain);
				}
			}
		}
		else {
			reset(startChunk);
		}
	}
	
	private void reset(TileEntityChunkData data) {
		startChunk = (TileEntityChunkCastle) data;
		chunkX = startChunk.chunkX;
		chunkZ = startChunk.chunkZ;
		demonPollution = getDemonPollutionFromPlain(startChunk);
	}
	
	private static float getDemonPollutionFromPlain(TileEntityChunkPlain plain) {
		float p = plain.productivity - 100.0F;
		if (p < 0.0F) {
			p = 0.0F;
		}
		else if (p > 100.0F) {
			p = 100.0F;
		}
		return p / 100.0F;
	}
	
	private static float getDemonBaseFromPlain(TileEntityChunkData data) {
		float p = 200.0F - data.productivity;
		if (p < 0.0F) {
			p = 0.0F;
		} 
		else if (p > 100.0F) {
			p = 100.0F;
		}
		return p / 100.0F;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("demonWanderer_chunkX")) {
			chunkX = nbt.getInteger("demonWanderer_chunkX");
		}
		if (nbt.hasKey("demonWanderer_chunkZ")) {
			chunkZ = nbt.getInteger("demonWanderer_chunkZ");
		}
		if (nbt.hasKey("demonWanderer_demonPollution")) {
			demonPollution = nbt.getFloat("demonWanderer_demonPollution");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("demonWanderer_chunkX", chunkX);
		nbt.setInteger("demonWanderer_chunkZ", chunkZ);
		nbt.setFloat("demonWanderer_demonPollution", demonPollution);
	}
	
}
