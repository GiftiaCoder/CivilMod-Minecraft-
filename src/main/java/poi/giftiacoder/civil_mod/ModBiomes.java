package poi.giftiacoder.civil_mod;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import poi.giftiacoder.civil_mod.world.BiomeCivil;

public class ModBiomes {

	public static final Biome CIVIL_MAIN = new BiomeCivil();
	
	public static void registerBiomes() {
		initBiome(CIVIL_MAIN, "Civil", BiomeType.WARM, BiomeDictionary.Type.DENSE);
	}
	
	private static Biome initBiome(Biome biome, String name, BiomeType biomeType, BiomeDictionary.Type... types) {
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addBiome(biomeType, new BiomeEntry(biome, 10));
		BiomeManager.addSpawnBiome(biome);
		return biome;
	}
	
}
