package poi.giftiacoder.civil_mod.tileentity.util;

import java.util.HashSet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;

public class ChunkSet extends HashSet<Long> {
	
	public void add(int x, int z) {
		super.add(pack(x, z));
	}
	
	public boolean contains(int x, int z) {
		return super.contains(pack(x, z));
	}
	
	public void remove(int x, int z) {
		super.remove(pack(x, z));
	}
	
	public static int unpackX(long v) {
		return (int) (0x00000000ffffffffL & (v >> 32));
	}
	
	public static int unpackZ(long v) {
		return (int) (0x00000000ffffffffL & v);
	}
	
	public static long pack(int x, int z) {
		return (0xffffffff00000000L & ((long)x << 32)) | (0x00000000ffffffffL & (long)z);
	}
	
	public static TileEntityChunkData getChunkData(World world, long v) {
		return TileEntityChunkData.getChunkData(world, unpackX(v), unpackZ(v));
	}
	
	public void readFromNBT(String key, NBTTagCompound nbt) {
		int[] list = nbt.getIntArray(key);
		for (int i = 0; i < list.length; ) {
			int x = list[i];
			int z = list[++i];
			add(x, z);
			++i;
		}
	}
	
	public void writeToNBT(String key, NBTTagCompound nbt) {
		int[] list = new int[size() * 2];
		int i = 0;
		for (Long v : this) {
			list[i] = unpackX(v);
			list[++i] = unpackZ(v);
			++i;
		}
		nbt.setIntArray(key, list);
	}
	
}
