package poi.giftiacoder.civil_mod;

import net.minecraftforge.fml.common.registry.GameRegistry;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkMaster;

public class ModTileEntities {
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityChunkMaster.class, R.TileEntities.ID_CHUNK_MASTER);
	}
	
}
