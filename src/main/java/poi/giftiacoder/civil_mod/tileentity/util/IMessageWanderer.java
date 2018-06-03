package poi.giftiacoder.civil_mod.tileentity.util;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;

public interface IMessageWanderer {
	
	public void wander();
	
	public void readFromNBT(NBTTagCompound nbt);
	
	public void writeToNBT(NBTTagCompound nbt);
	
	public static void spawnEntity(TileEntityChunkData data, Entity entity) {
		World world = data.getWorld();
		
		world.spawnEntity(entity);
		
		int spawnX = world.rand.nextInt(16);
		int spawnZ = world.rand.nextInt(16);
		int spawnY = data.heigthMap[spawnX][spawnZ];
		
		entity.setPositionAndUpdate(data.chunkX << 4 | spawnX, spawnY + 1, data.chunkZ << 4 | spawnZ);
	}
	
}
