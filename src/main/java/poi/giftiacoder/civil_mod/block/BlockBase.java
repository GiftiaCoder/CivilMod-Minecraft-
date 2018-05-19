package poi.giftiacoder.civil_mod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import poi.giftiacoder.civil_mod.ModBlocks;
import poi.giftiacoder.civil_mod.ModItems;
import poi.giftiacoder.civil_mod.R;

public class BlockBase extends Block {

	public BlockBase(R.EnumModBlocks blockInfo, Material materialIn) {
		super(materialIn);
		setRegistryName(blockInfo.getRegistryName());
		setUnlocalizedName(blockInfo.getUnlocalizedName());

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
}
