package poi.giftiacoder.civil_mod.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import poi.giftiacoder.civil_mod.ModDimensions;
import poi.giftiacoder.civil_mod.ModTileEntities;
import poi.giftiacoder.civil_mod.R;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkMaster;

public class CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void register() {
		ModTileEntities.registerTileEntities();
		ModDimensions.registerDimensions();
	}
	
}
