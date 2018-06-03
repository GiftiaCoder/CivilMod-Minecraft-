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

	public static final int CIVIL_WORLD_LOW_LEVEL = 64;
	public static final int CIVIL_WORLD_TOP_LEVEL = 114;
	public static final float MIN_CASTLE_CHUNK_PRODUCTIVITY = 22.0F;
	
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
		//return new ChunkGeneratorTest(world);
	}
	
	@Override
	public boolean canRespawnHere() {
		return true;
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return false;
	}
	
}
