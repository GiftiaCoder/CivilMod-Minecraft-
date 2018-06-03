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
import poi.giftiacoder.civil_mod.algo.TinyPerlinNoise;
import poi.giftiacoder.civil_mod.civilmaterial.Resources;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkPlain;
import poi.giftiacoder.civil_mod.world.civil.DimensionCivil;

public class ChunkGeneratorCivil implements IChunkGenerator {
	
	public static final float PRODUCTIVITY_ZERO_BOUND = 256.0F;
	
	private World world;
	private PerlinNoise noise;
	private TinyPerlinNoise tinyNoise;
	
	public ChunkGeneratorCivil(World worldIn) {
		this.world = worldIn;
		this.noise = new PerlinNoise(0.5F, 4, worldIn.getSeed());
		this.tinyNoise = new TinyPerlinNoise(world.getSeed() * 40283);
		Resources.setSeed(worldIn.getSeed());
	}
	
	@Override
	public Chunk generateChunk(int x, int z) {
		final float threshold = 0.434F;
		
		float chunkPriceWeight = 0;
		
		ChunkPrimer primer = new ChunkPrimer();
		int lowHeightCount = 0, topHeightCount = 0;
		int[][] heightMap = new int[16][16];
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				primer.setBlockState(i, 0, j, Blocks.BEDROCK.getDefaultState());
				
				float noise = this.noise.perlinNoise(((x << 4) + i) / 128.0F, ((z << 4) + j) / 128.0F);
				
				chunkPriceWeight += noise;
				
				int height = (int) ((noise - threshold) * 800.0F);
				//height = height < 2 ? 2 : height;
				height = height < DimensionCivil.CIVIL_WORLD_LOW_LEVEL ? DimensionCivil.CIVIL_WORLD_LOW_LEVEL : height;
				height = height > DimensionCivil.CIVIL_WORLD_TOP_LEVEL ? DimensionCivil.CIVIL_WORLD_TOP_LEVEL : height;
				
				int h = 1;
				for (; h < height; ++h) {
					primer.setBlockState(i, h, j, Blocks.STONE.getDefaultState());
				}
				//for (; h < DimensionCivil.CIVIL_WORLD_TOP_LEVEL; ++h) {
				//	primer.setBlockState(i, h, j, Blocks.GLASS.getDefaultState());
				//}
				primer.setBlockState(i, h, j, Blocks.GRASS.getDefaultState());
				//primer.setBlockState(i, h, j, Blocks.GLASS.getDefaultState());
				if (i == 0 || j == 0 || i == 15 || j == 15) {
					primer.setBlockState(i, h, j, Blocks.IRON_BLOCK.getDefaultState());
				}
				
				if (height == DimensionCivil.CIVIL_WORLD_LOW_LEVEL) {
					++lowHeightCount;
				}
				else if (height == DimensionCivil.CIVIL_WORLD_TOP_LEVEL) {
					++topHeightCount;
				}
				
				heightMap[i][j] = height;
			}
		}
		TileEntityChunkData.perbindChunkDataWithChunkPrimer(primer);
		
		Chunk chunk = new Chunk(this.world, primer, x, z);
		
		TileEntityChunkData chunkData = TileEntityChunkData.ChunkType.getChunkType(lowHeightCount, topHeightCount).createChunkData(
				world, PRODUCTIVITY_ZERO_BOUND - chunkPriceWeight, x, z, heightMap);
		chunkData.bindChunkDataWithChunk(chunk);
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	@Override
	public void populate(int x, int z) {
		TileEntityChunkData chunkData = TileEntityChunkData.getChunkData(world, x, z);
		if (chunkData instanceof TileEntityChunkPlain) {
			TileEntityChunkPlain chunkPlain = (TileEntityChunkPlain)chunkData;
			// TODO
		}
	}
	
	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) { return false; }

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) { return new ArrayList<>(); }

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) { return null; }

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) { return false; }

}
