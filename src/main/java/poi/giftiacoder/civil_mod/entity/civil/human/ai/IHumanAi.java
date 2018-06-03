package poi.giftiacoder.civil_mod.entity.civil.human.ai;

import net.minecraft.nbt.NBTTagCompound;
import poi.giftiacoder.civil_mod.entity.civil.human.EntityHumanBase;

public interface IHumanAi {
	
	public void updateAi();
	
	public void onEnterChunk(int oldChunkX, int oldChunkZ, int newChunkX, int newChunkZ);
	
	public void writeToNBT(NBTTagCompound nbt);
	
	public void readFromNBT(NBTTagCompound nbt);
	
}
