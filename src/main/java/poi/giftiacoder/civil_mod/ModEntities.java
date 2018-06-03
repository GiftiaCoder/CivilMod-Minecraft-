package poi.giftiacoder.civil_mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import poi.giftiacoder.civil_mod.entity.EntityHuman;
import poi.giftiacoder.civil_mod.entity.civil.EntityHumanCivil;
import poi.giftiacoder.civil_mod.entity.civil.human.EntityHumanBase;
import poi.giftiacoder.civil_mod.entity.renderer.RenderHuman;

public class ModEntities 
{
	private static int nextId = 0;
	
	public static void register()
	{
		EntityRegistry.registerModEntity(new ResourceLocation(R.MODID, "human_civil"), EntityHumanCivil.class, "human_civil", ++nextId, CivilMod.instance, 80, 2, true, 0x00005CE8, 0x00FFFFFF);
		EntityRegistry.registerModEntity(new ResourceLocation(R.MODID, "human_base"), EntityHumanBase.class, "human_base", ++nextId, CivilMod.instance, 80, 2, true, 0x002248DE, 0x00FFFFFF);
		//EntityRegistry.registerModEntity(new ResourceLocation(R.MODID, "human"), EntityHuman.class, "human", ++nextId, CivilMod.instance, 80, 2, true, 0x003f0000, 0x008c0c8c);
	}
	
	public static void registerRenders()
	{
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
		
		manager.entityRenderMap.put(EntityHumanCivil.class, new RenderBiped<>(manager, new ModelPlayer(0.0F, true), 0.6F));
		manager.entityRenderMap.put(EntityHumanBase.class, new RenderBiped<>(manager, new ModelPlayer(0.0F, true), 0.6F));
		//manager.entityRenderMap.put(EntityHuman.class, new RenderHuman(manager));
	}
}
