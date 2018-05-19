package poi.giftiacoder.civil_mod.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import poi.giftiacoder.civil_mod.algo.PerlinNoise;
import poi.giftiacoder.civil_mod.building.ModBuildings;

public class ChunkGeneratorCivil implements IChunkGenerator {

	private World world;
	
	public ChunkGeneratorCivil(World worldIn) {
		this.world = worldIn;
	}
	
	@Override
	public Chunk generateChunk(int x, int z) {
		final float threshold = 0.375F;
		
		float chunkPriceWeight = 0;
		
		ChunkPrimer primer = new ChunkPrimer();
		int highLevelCounter = 0;
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				primer.setBlockState(i, 0, j, Blocks.BEDROCK.getDefaultState());
				
				float noise = PerlinNoise.perlinNoise(((x << 4) + i) / 128.0F, ((z << 4) + j) / 128.0F, 0.4F, 8);
				int height = (int) ((noise - threshold) * 500.0F);
				height = height < 63 ? 63 : height;
				height = height > 95 ? 95 : height;
				
				for (int h = 1; h < height; ++h) {
					primer.setBlockState(i, h, j, Blocks.STONE.getDefaultState());
				}
				
				primer.setBlockState(i, height, j, Blocks.GRASS.getDefaultState());
				if (height > 63) {
					++highLevelCounter;
					
					if (this.world.rand.nextInt(6) == 0) {
						int r = this.world.rand.nextInt(24);
						if (r < 8) {
							primer.setBlockState(i, height, j, Blocks.LOG.getDefaultState());
							primer.setBlockState(i, height + 1, j, Blocks.LEAVES.getDefaultState());
						}
						else if (r < 12) {
							primer.setBlockState(i, height + 1, j, Blocks.LOG.getDefaultState());
						}
						else if (r < 14) {
							primer.setBlockState(i, height + 1, j, Blocks.RED_FLOWER.getDefaultState());
						}
						else if (r < 16) {
							primer.setBlockState(i, height + 1, j, Blocks.YELLOW_FLOWER.getDefaultState());
						}
						else if (r < 20) {
							primer.setBlockState(i, height + 1, j, Blocks.BROWN_MUSHROOM.getDefaultState());
						}
						else if (r < 24) {
							primer.setBlockState(i, height + 1, j, Blocks.RED_MUSHROOM.getDefaultState());
						}
					}
				}
				else {
					chunkPriceWeight += noise;
				}
			}
		}
		
		Chunk chk = new Chunk(this.world, primer, x, z);
		if (highLevelCounter == 0) {
			int r = this.world.rand.nextInt(28);
			if (r < 22) {
				ModBuildings.HOUSE.generate(chk, 63);
			}
			else if (r < 24) {
				ModBuildings.BARRACK.generate(chk, 63);
			}
			else if (r == 24) {
				ModBuildings.CHURCH.generate(chk, 63);
			}
			else if (r == 25) {
				ModBuildings.CASTLE.generate(chk, 63);
			}
		}
		chk.generateSkylightMap();
		return chk;
	}

	@Override
	public void populate(int x, int z) {
	}
	
	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return new ArrayList<>();
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored) {
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}

	

}
