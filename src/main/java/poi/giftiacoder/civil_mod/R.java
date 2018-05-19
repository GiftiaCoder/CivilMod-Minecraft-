package poi.giftiacoder.civil_mod;

import net.minecraft.block.Block;
import net.minecraft.world.DimensionType;
import poi.giftiacoder.civil_mod.world.civil.DimensionCivil;

public interface R  {
	
	public static final String MODID = "civil_mod";
	public static final String NAME = "Civil Mod";
	public static final String VERSION = "1.0";
	
	public static final String ACCEPTED_VERSIONS = "{1.12.2}";
	
	public static final String CLIENT_PROXY_CLASS = "poi.giftiacoder.civil_mod.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "poi.giftiacoder.civil_mod.proxy.CommonProxy";
	
	public interface TileEntities {
		public static final String ID_CHUNK_MASTER = R.MODID + ":chunk_master";
	}
	
	public interface Dimensions {
		public static final DimensionType CIVIL = DimensionType.register("Civil", "_civil", 10, DimensionCivil.class, false);
	}
	
	public static enum EnumModBlocks {
		
		CHUNK_MASTER("chunk_master");
		
		private String unlocalizedName;
		private String registryName;
		
		private EnumModBlocks(String name) {
			unlocalizedName = name;
			registryName = name;
		}
		
		public void initName(Block block) {
			block.setUnlocalizedName(unlocalizedName);
			block.setRegistryName(R.MODID, registryName);
		}
		
		public String getRegistryName() {
			return registryName;
		}
		
		public String getUnlocalizedName() {
			return unlocalizedName;
		}
	}
}
