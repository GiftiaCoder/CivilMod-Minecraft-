package poi.giftiacoder.civil_mod;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {

	public static void registerDimensions() {
		DimensionManager.registerDimension(R.Dimensions.CIVIL.getId(), R.Dimensions.CIVIL);
	}
	
}
