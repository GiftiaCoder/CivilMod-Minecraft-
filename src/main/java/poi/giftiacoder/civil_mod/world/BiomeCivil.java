package poi.giftiacoder.civil_mod.world;

import net.minecraft.world.biome.Biome;

public class BiomeCivil extends Biome {

	public BiomeCivil() {
		super(new BiomeProperties("Civil")
				.setBaseHeight(1.5F)
				.setHeightVariation(1.2F)
				.setTemperature(0.6F)
				.setRainDisabled()
				.setWaterColor(0x00CC00CC));
	}

}
