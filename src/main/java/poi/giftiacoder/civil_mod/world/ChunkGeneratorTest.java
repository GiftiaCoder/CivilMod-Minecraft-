package poi.giftiacoder.civil_mod.world;

import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import poi.giftiacoder.civil_mod.algo.TinyPerlinNoise;

public class ChunkGeneratorTest implements IChunkGenerator {

	private World world;
	private TinyPerlinNoise noise;
	
	public ChunkGeneratorTest(World worldIn) {
		world = worldIn;
		noise = new TinyPerlinNoise(world.getSeed());
	}
	
	@Override
	public Chunk generateChunk(int x, int z) {
		ChunkPrimer primer = new ChunkPrimer();
		
		int bx = x << 4, bz = z << 4;
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				float n = noise.getNoise(bx + i, bz + j);
				int height = (int) (n * 12.0F) + 64;
				for (int h = 0; h < height; ++h) {
					primer.setBlockState(i, h, j, Blocks.DIAMOND_BLOCK.getDefaultState());
				}
			}
		}
		
		Chunk chunk = new Chunk(world, primer, x, z);
		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public void populate(int x, int z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		// TODO Auto-generated method stub
		return false;
	}

}
