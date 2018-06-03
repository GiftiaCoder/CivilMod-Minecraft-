package poi.giftiacoder.civil_mod.tileentity.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkCastle;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkPlain;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkPlain.BuildingType;

public class TravellerGenWanderer implements IMessageWanderer {

	private TileEntityChunkCastle startChunk;
	
	private int chunkX, chunkZ;
	private float travellerAffect = 0;
	
	public TravellerGenWanderer(TileEntityChunkCastle chunk) {
		reset(chunk);
	}
	
	@Override
	public void wander() {
		TileEntityChunkData curr = TileEntityChunkData.getChunkData(startChunk.getWorld(), chunkX, chunkZ);
		if (curr != null && curr instanceof TileEntityChunkPlain) {
			TileEntityChunkPlain next = getNextPlain((TileEntityChunkPlain)curr, startChunk.getWorld());
			if (next != null) {
				chunkX = next.chunkX;
				chunkZ = next.chunkZ;
				
				if (next.buildingType != BuildingType.WASTELAND) {
					travellerAffect += getTravellerAffectFromChunk(next);
				}
				else {
					travellerAffect -= getTravellAffectToChunk(next);
					if (travellerAffect < 0) {
						
						Entity entity = new EntityVillager(next.getWorld());
						IMessageWanderer.spawnEntity(next, entity);
						
						reset(startChunk);
					}
				}
				
				return;
			}
		}
		reset(startChunk);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("travellerWanderer_chunkX")) {
			chunkX = nbt.getInteger("travellerWanderer_chunkX");
		}
		if (nbt.hasKey("travellerWanderer_chunkZ")) {
			chunkZ = nbt.getInteger("travellerWanderer_chunkZ");
		}
		if (nbt.hasKey("travellerWanderer_travellerAffect")) {
			travellerAffect = nbt.getFloat("travellerWanderer_travellerAffect");
		}
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("travellerWanderer_chunkX", chunkX);
		nbt.setInteger("travellerWanderer_chunkZ", chunkZ);
		nbt.setFloat("travellerWanderer_travellerAffect", travellerAffect);
	}

	private void reset(TileEntityChunkCastle chunk) {
		startChunk = chunk;
		chunkX = chunk.chunkX;
		chunkZ = chunk.chunkZ;
		travellerAffect = getTravellerAffectFromChunk(chunk);
	}
	
	private TileEntityChunkPlain getNextPlain(TileEntityChunkPlain plain, World world) {
		int n = 0;
		TileEntityChunkPlain[] lowerList = new TileEntityChunkPlain[4];
		TileEntityChunkData cd = null;
		
		cd = TileEntityChunkData.getChunkData(world, chunkX - 1, chunkZ);
		if (isValidNext(plain, cd)) {
			lowerList[n] = (TileEntityChunkPlain) cd;
			++n;
		}
		cd = TileEntityChunkData.getChunkData(world, chunkX + 1, chunkZ);
		if (isValidNext(plain, cd)) {
			lowerList[n] = (TileEntityChunkPlain) cd;
			++n;
		}
		cd = TileEntityChunkData.getChunkData(world, chunkX, chunkZ - 1);
		if (isValidNext(plain, cd)) {
			lowerList[n] = (TileEntityChunkPlain) cd;
			++n;
		}
		cd = TileEntityChunkData.getChunkData(world, chunkX, chunkZ + 1);
		if (isValidNext(plain, cd)) {
			lowerList[n] = (TileEntityChunkPlain) cd;
			++n;
		}
		
		if (n > 0) {
			return lowerList[world.rand.nextInt(n)];
		}
		return null;
	}
	
	private static boolean isValidNext(TileEntityChunkData curr, TileEntityChunkData data) {
		if (data != null && data instanceof TileEntityChunkPlain) {
			TileEntityChunkPlain plain = (TileEntityChunkPlain) data;
			if (plain.productivity < curr.productivity) {
				return true;
			}
		}
		return false;
	}
	
	private static float getTravellerAffectFromChunk(TileEntityChunkPlain plain) {
		float val = plain.productivity - 100.0F;
		if (val >= 50.0F) {
			val = 50.0F;
		}
		else if (val <= 0.0F) {
			val = 0.0F;
		}
		return val;
	}
	
	private static float getTravellAffectToChunk(TileEntityChunkPlain plain) {
		float val = plain.productivity - 120.0F;
		if (val >= 15.0F) {
			val = 15.0F;
		}
		else if (val <= 0.0F) {
			val = 0.0F;
		}
		return val;
	}
	
}
