package poi.giftiacoder.civil_mod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import poi.giftiacoder.civil_mod.commands.CmdCheckChunkMaster;
import poi.giftiacoder.civil_mod.commands.CmdCheckResources;
import poi.giftiacoder.civil_mod.commands.CmdDimensionTeleport;
import poi.giftiacoder.civil_mod.eventhandler.RecordBuildingMacro;
import poi.giftiacoder.civil_mod.proxy.CommonProxy;

@Mod(modid = R.MODID, name = R.NAME, version = R.VERSION)
public class CivilMod  {
	
	@Mod.Instance(R.MODID)
	public static CivilMod instance;
	
	@SidedProxy(clientSide=R.CLIENT_PROXY_CLASS, serverSide=R.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void pre(FMLPreInitializationEvent event) {
		// TODO
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) throws Exception {
		proxy.register();
		
		MinecraftForge.EVENT_BUS.register(new RecordBuildingMacro());
	}
	
	@EventHandler
	public void post(FMLPostInitializationEvent event) {
		// TODO
	}
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		event.registerServerCommand(new CmdDimensionTeleport());
		event.registerServerCommand(new CmdCheckChunkMaster());
		event.registerServerCommand(new CmdCheckResources());
	}
}
