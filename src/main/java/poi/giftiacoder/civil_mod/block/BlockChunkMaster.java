package poi.giftiacoder.civil_mod.block;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import poi.giftiacoder.civil_mod.R;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkMaster;

public class BlockChunkMaster extends BlockBase implements ITileEntityProvider {
	
	public BlockChunkMaster() {
		super(R.EnumModBlocks.CHUNK_MASTER, Material.ROCK);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
		this.setSoundType(SoundType.STONE);
		this.disableStats();
		
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
	
	public int quantityDropped(Random random) {
		return 0;
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityChunkMaster();
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityChunkMaster();
	}
}
