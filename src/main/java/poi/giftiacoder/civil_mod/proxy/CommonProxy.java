package poi.giftiacoder.civil_mod.proxy;

import net.minecraft.item.Item;
import poi.giftiacoder.civil_mod.ModDimensions;
import poi.giftiacoder.civil_mod.ModEntities;
import poi.giftiacoder.civil_mod.ModTileEntities;

public class CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void register() {
		ModDimensions.registerDimensions();
		ModTileEntities.registerTileEntities();
		ModEntities.register();
	}
	
}
