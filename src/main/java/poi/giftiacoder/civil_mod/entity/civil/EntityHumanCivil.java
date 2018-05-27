package poi.giftiacoder.civil_mod.entity.civil;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityHumanCivil extends EntityLiving {

	private CivilHumanType humanType = CivilHumanType.TRAVELLER;
	
	public EntityHumanCivil(World worldIn) {
		super(worldIn);
	}

	public void setHumanType(CivilHumanType humanType) {
		this.humanType = humanType;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("humanType")) {
			humanType = CivilHumanType.values()[nbt.getInteger("humanType")];
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("humanType", humanType.ordinal());
	}
	
}
