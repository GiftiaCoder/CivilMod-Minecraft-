package poi.giftiacoder.civil_mod;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import poi.giftiacoder.civil_mod.civilmaterial.Resources;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;

@EventBusSubscriber
public class RegistryHandler {
	
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void registerBlock(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void registerModel(ModelRegistryEvent event) {
		System.out.println(event);
		for (Item item : ModItems.ITEMS) {
			CivilMod.proxy.registerItemRenderer(item, 0, "inventory");
		}
		for (Block block : ModBlocks.BLOCKS) {
			CivilMod.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
		}
	}
	
	public static class ResourceDataList extends ArrayList<Float> {}
	public static ResourceDataList[] resourceDataList = new ResourceDataList[Resources.values().length];
	static {
		for (int i = 0; i < resourceDataList.length; ++i) {
			resourceDataList[i] = new ResourceDataList();
		}
	}
	@SubscribeEvent
	public static void chunkResourceRecorder(EntityEvent.EnteringChunk event) throws IOException {
		if (event.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)event.getEntity();
			TileEntityChunkData data = TileEntityChunkData.getChunkData(player.world, event.getNewChunkX(), event.getNewChunkZ());
			if (data != null) {
				for (int i = 0; i < data.resourcesBasicAmount.length; ++i) {
					//rt.write(String.format("%s\t%f\n", Resources.values()[i].name().toLowerCase(), data.resourcesBasicAmount[i]));
					resourceDataList[i].add(data.resourcesBasicAmount[i]);
				}
			}
		}
	}
	public static void writeResourceData() throws IOException {
		BufferedWriter rt = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("chunk_resource.txt", true)));
		for (int i = 0; i < Resources.values().length; ++i) {
			rt.write(Resources.values()[i].name().toLowerCase() + "\t");
			for (float f : resourceDataList[i]) {
				rt.write(f + "\t");
			}
			rt.write("\n");
		}
		rt.write("\n");
		rt.flush();
		rt.close();
	}
	
}
