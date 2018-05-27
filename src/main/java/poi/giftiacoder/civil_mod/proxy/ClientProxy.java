package poi.giftiacoder.civil_mod.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import poi.giftiacoder.civil_mod.ModEntities;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		super.registerItemRenderer(item, meta, id);
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void register() {
		super.register();
		ModEntities.registerRenders();
	}
}
