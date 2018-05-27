package poi.giftiacoder.civil_mod.entity.civil;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityHumanBarbarian extends EntityLiving {
	
	public static final int BARBARIAN_AI_UPDATE_PERIOD = 20 * 4;
	
	public static enum BarbarianType {
		LEADER {
			@Override
			public void updateEntityAI(EntityHumanBarbarian entity) {
				// TODO Auto-generated method stub
				
			}
		}, ARCHER, CAVALRY , WARRIOR;
		
		public void updateEntityAI(EntityHumanBarbarian entity) {
			// leader AI will rewrite this function, do not judge if current entity is leader
			if (entity.leader != null && entity.leader.isEntityAlive()) {
				// TODO follow the leader
			}
			else {
				entity.leader = null;
				// TODO goto chunk where productivity is much lower in order to find a leader
			}
		}
	};
	
	private BarbarianType barbarianType = BarbarianType.WARRIOR;
	private int aiUpdateTick = 0;
	private EntityHumanBarbarian leader = null;
	
	public EntityHumanBarbarian(World worldIn) {
		super(worldIn);
		setSize(0.8F, 1.8F);
		
		
		aiUpdateTick = worldIn.rand.nextInt(BARBARIAN_AI_UPDATE_PERIOD);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if (++aiUpdateTick >= BARBARIAN_AI_UPDATE_PERIOD) {
			barbarianType.updateEntityAI(this);
			aiUpdateTick = 0;
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("barbarianType")) {
			barbarianType = BarbarianType.values()[nbt.getInteger("barbarianType")];
		}
		if (nbt.hasKey("aiUpdateTick")) {
			aiUpdateTick = nbt.getInteger("aiUpdateTick");
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("barbarianType", barbarianType.ordinal());
		nbt.setInteger("aiUpdateTick", aiUpdateTick);
		super.writeEntityToNBT(nbt);
	}
	
}
