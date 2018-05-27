package poi.giftiacoder.civil_mod;

import net.minecraftforge.fml.common.registry.GameRegistry;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkCastle;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkPlain;

public class ModTileEntities {
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityChunkData.class, R.TileEntities.ID_CHUNK_DATA);
		GameRegistry.registerTileEntity(TileEntityChunkPlain.class, R.TileEntities.ID_CHUNK_PLAIN);
		GameRegistry.registerTileEntity(TileEntityChunkCastle.class, R.TileEntities.ID_CHUNK_CASTLE);
	}
	
}
