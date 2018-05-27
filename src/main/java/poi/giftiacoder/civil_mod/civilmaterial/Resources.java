package poi.giftiacoder.civil_mod.civilmaterial;

import net.minecraft.nbt.NBTTagCompound;
import poi.giftiacoder.civil_mod.algo.PerlinNoise;
import poi.giftiacoder.civil_mod.algo.Primzahl;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData.ChunkType;

public enum Resources {
	WATER(1.5F, 8.0F, 0.85F), 
	
	// food (parent: water)
	WHEAT(0.65F, 4.5F, 0.84F, WATER), 
	POTATO(0.78F, 6.0F, 1.0F, WATER), 
	SPICE(0.16F, 16.0F, 0.1F, WATER), 
	SUGAR(0.2F, 12.5F, 0.2F, WATER), 
	
	EGG(0.14F, 4.0F, 0.6F, WHEAT), 
	
	PASTURE(0.88F, 12.0F, 1.0F, WATER), 
	
	PORK(0.2F, 6.0F, 1.0F, PASTURE), 
	BEAF(0.12F, 6.0F, 1.0F, PASTURE), 
	MUTTON(0.12F, 6.0F, 1.0F, PASTURE), 
	MILK(1.0F, 2.0F, 1.0F, BEAF), 
	
	HORSE(0.85F, 14.0F, 0.16F, PASTURE), 
	VENISON(0.09F, 14.0F, 0.11F, PASTURE), 
	
	// material
	STONE(0.45F, 6.0F, 0.6F), 
	IRON_ORE(0.12F, 14.0F, 0.74F), 
	GOLD_ORE(0.45F, 14.0F, 1.0F, IRON_ORE), 
	RUBY(0.98F, 16.0F, 1.0F, STONE, IRON_ORE), 
	DIAMOND(0.72F, 16.0F, 1.0F, STONE, IRON_ORE), 
	
	// forest (parent: water)
	WOOD(1.0F, 8.0F, 1.0F, WATER), 
	APPLE(0.12F, 8.0F, 1.0F, WOOD), 
	ORANGE(0.12F, 8.0F, 1.0F, WOOD), 
	COCOA(0.08F, 12.0F, 0.5F, WOOD);
	
	protected float weight = 1.0F;
	protected float range = 4.0F;
	protected float proportion = 1.0F;
	protected PerlinNoise noise = null;
	protected Resources[] parents;
	
	private String nbtName;
	
	private Resources(float weight, float range, float proportion, Resources ... parents) {
		this.weight = weight;
		this.range = range;
		this.proportion = proportion;
		this.parents = parents;
		
		this.nbtName = "resource_" + name().toLowerCase();
		
		System.out.println(this + ": " + parents.length);
	}
	
	public void setNoise(PerlinNoise noise) {
		this.noise = noise;
	}
	
	public float getWeight(int x, int z) {
		final float minN = 0.4F, maxN = 0.55F;
		float n = noise.perlinNoise((float) x / range, (float) z / range);
		if (n < minN) {
			n = minN;
		} else if (n > maxN) {
			n = maxN;
		}
		n = (n - minN) * (1.0F / (maxN - minN));
		
		float base = n  - (1.0F - proportion);
		float val = (base >= 0.0F ? base : 0.0F) * (1.0F / proportion) * weight;
		val = val <= 1.0F ? val : 1.0F;
		
		for (Resources res : parents) {
			val *= res.getWeight(x, z);
		}
		
		return val;
	}
	
	public static void readFromNBT(TileEntityChunkData data, NBTTagCompound nbt) {
		for (int i = 0; i < data.resourcesBasicAmount.length; ++i) {
			Resources res = values()[i];
			if (nbt.hasKey(res.nbtName)) {
				data.resourcesBasicAmount[i] = nbt.getFloat(res.nbtName);
			}
		}
	}
	
	public static void writeToNBT(TileEntityChunkData data, NBTTagCompound nbt) {
		for (int i = 0; i < data.resourcesBasicAmount.length; ++i) {
			Resources res = values()[i];
			nbt.setFloat(res.nbtName, data.resourcesBasicAmount[i]);
		}
	}
	
	public static void setSeed(long seed) {
		for (int i = 0; i < Resources.values().length; ++i) {
			Resources.values()[i].noise = new PerlinNoise(0.5F, 2, seed + Primzahl.PRIMZAHLS[i]);
		}
	}
}
