package poi.giftiacoder.civil_mod.tileentity;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import poi.giftiacoder.civil_mod.tileentity.util.ChunkSet;

public class TileEntityChunkCastle extends TileEntityChunkPlain implements ITickable {

	public static final int CASTLE_AI_UPDATE_PERIOD = 20 * 3;
	
	private int tickCounter = 0;
	public ChunkSet territorySet = new ChunkSet();
	public ChunkSet expandSet = new ChunkSet();
	
	public float sumProductivity = 0;
	
	public IBlockState campColorBlock;
	
	public TileEntityChunkCastle() {}
	
	public TileEntityChunkCastle(World world, float productivity, int chunkX, int chunkZ) {
		super(world, BuildingType.CASTLE, productivity, chunkX, chunkZ);
		campColorBlock = Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, 
				EnumDyeColor.values()[world.rand.nextInt(EnumDyeColor.values().length)]);
		
		tickCounter = world.rand.nextInt(CASTLE_AI_UPDATE_PERIOD);
		
		territorySet.add(chunkX, chunkZ);
		expandSet.add(chunkX, chunkZ + 1);
		expandSet.add(chunkX, chunkZ - 1);
		expandSet.add(chunkX + 1, chunkZ);
		expandSet.add(chunkX - 1, chunkZ);
	}
	
	protected void updateCastleAI() {
		for (Long c : territorySet) {
			TileEntityChunkData chunkData = ChunkSet.getChunkData(world, c);
			if (chunkData != null) {
				sumProductivity += chunkData.productivity / 4.0F;
			}
		}
		tryExpand();
	}
	
	private void tryExpand() {
		//float maxProductivity = 0;
		float maxWeight = 0;
		TileEntityChunkPlain chunkPlainToExpand = null;
		
		Iterator<Long> cki = expandSet.iterator();
		while (cki.hasNext()) {
			TileEntityChunkData chunkData = ChunkSet.getChunkData(world, cki.next());
			
			if (chunkData == null) { // if chunk not generated, we do not expand to this chunk
				continue;
			}
			
			// get wasteland on plain, else remove from current set
			if (chunkData.chunkType == ChunkType.PLAIN) {
				TileEntityChunkPlain chunkPlain = (TileEntityChunkPlain)chunkData;
				if (chunkPlain.buildingType == BuildingType.WASTELAND) {
					
					float expandWeight = getExpandWeight(chunkPlain);
					
					//if (chunkPlain.productivity > maxProductivity && chunkPlain.productivity <= sumProductivity) {
					if (expandWeight > maxWeight && chunkPlain.productivity < sumProductivity) {
						//maxProductivity = chunkPlain.productivity;
						maxWeight = expandWeight;
						chunkPlainToExpand = chunkPlain;
					}
					continue;
				}
			}
			cki.remove();
		}
		
		if (chunkPlainToExpand != null) {
			sumProductivity -= chunkPlainToExpand.productivity;
			
			chunkPlainToExpand.setTerritoryOf(this);
			addTerritory(chunkPlainToExpand);
			
			//System.out.printf("(%d, %d), (%d, %d)\n", chunkX, chunkZ, chunkPlainToExpand.chunkX, chunkPlainToExpand.chunkZ);
		}
	}
	
	private float getExpandWeight(TileEntityChunkPlain data) {
		float dx = data.chunkX - chunkX, dz = data.chunkZ - chunkZ;
		return (float) (data.productivity * 1.413F / Math.sqrt(dx * dx + dz * dz));
	}
	
	private void addTerritory(TileEntityChunkPlain chunkPlainToExpand) {
		territorySet.add(chunkPlainToExpand.chunkX, chunkPlainToExpand.chunkZ);
		expandSet.remove(chunkPlainToExpand.chunkX, chunkPlainToExpand.chunkZ);
		
		if (! territorySet.contains(chunkPlainToExpand.chunkX + 1, chunkPlainToExpand.chunkZ)) {
			expandSet.add(chunkPlainToExpand.chunkX + 1, chunkPlainToExpand.chunkZ);
		}
		if (! territorySet.contains(chunkPlainToExpand.chunkX - 1, chunkPlainToExpand.chunkZ)) {
			expandSet.add(chunkPlainToExpand.chunkX - 1, chunkPlainToExpand.chunkZ);
		}
		if (! territorySet.contains(chunkPlainToExpand.chunkX, chunkPlainToExpand.chunkZ + 1)) {
			expandSet.add(chunkPlainToExpand.chunkX, chunkPlainToExpand.chunkZ + 1);
		}
		if (! territorySet.contains(chunkPlainToExpand.chunkX, chunkPlainToExpand.chunkZ - 1)) {
			expandSet.add(chunkPlainToExpand.chunkX, chunkPlainToExpand.chunkZ - 1);
		}
	}

	@Override
	public void update() {
		if (++tickCounter >= CASTLE_AI_UPDATE_PERIOD) {
			updateCastleAI();
			tickCounter = 0;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("tickCounter")) {
			tickCounter = nbt.getInteger("tickCounter");
		}
		if (nbt.hasKey("sumProductivity")) {
			sumProductivity = nbt.getFloat("sumProductivity");
		}
		if (nbt.hasKey("campColorBlock")) {
			campColorBlock = Block.getStateById(nbt.getInteger("campColorBlock"));
		}
		territorySet.readFromNBT("territorySet", nbt);
		expandSet.readFromNBT("expandSet", nbt);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("tickCounter", tickCounter);
		nbt.setFloat("sumProductivity", sumProductivity);
		nbt.setInteger("campColorBlock", Block.getStateId(campColorBlock));
		territorySet.writeToNBT("territorySet", nbt);
		expandSet.writeToNBT("expandSet", nbt);
		return super.writeToNBT(nbt);
	}
}
