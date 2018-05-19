package poi.giftiacoder.civil_mod.world.civil;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import poi.giftiacoder.civil_mod.ModBiomes;
import poi.giftiacoder.civil_mod.R;
import poi.giftiacoder.civil_mod.world.ChunkGeneratorCivil;
import poi.giftiacoder.civil_mod.world.ChunkGeneratorTest;

public class DimensionCivil extends WorldProvider {

	public DimensionCivil() {
		this.biomeProvider = new BiomeProviderSingle(ModBiomes.CIVIL_MAIN);
	}
	
	@Override
	public DimensionType getDimensionType() {
		return R.Dimensions.CIVIL;
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorCivil(this.world);
		//return new ChunkGeneratorTest(world, getSeed(), null);
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return false;
	}
	
}
