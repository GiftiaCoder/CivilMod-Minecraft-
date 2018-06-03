package poi.giftiacoder.civil_mod.entity.civil.human;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import poi.giftiacoder.civil_mod.entity.civil.human.ai.HumanAiTravller;
import poi.giftiacoder.civil_mod.entity.civil.human.ai.IHumanAi;
import poi.giftiacoder.civil_mod.util.EntityUtil;

@EventBusSubscriber
public class EntityHumanBase extends EntityCreature {
	
	private static final List<Entity> EMPTY_HUMAN_SIGHT = new ArrayList<>();
	
	private int updatePeriod = 0;
	private HumanType humanType = HumanType.TRAVELLER;
	
	public List<Entity> entityInSight = EMPTY_HUMAN_SIGHT;
	
	public IHumanAi travellerAi = new HumanAiTravller(this);
	
	public EntityHumanBase(World worldIn) {
		super(worldIn);
		updatePeriod = worldIn.rand.nextInt(20);
		setCustomNameTag("search");
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		
		if (! world.isRemote) {
			if (++updatePeriod >= 20) {
				humanType = humanType.update(this);
				updatePeriod = 0;
			}
			
			// TODO
		}
	}
	
	public void updateSight() {
		List<Entity> sight = new ArrayList<>();
		EntityUtil.getEntitiesByRange(this, 1, sight);
		entityInSight = sight;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("humanTypeIdx")) {
			humanType = HumanType.values()[nbt.getInteger("humanTypeIdx")];
		}
		travellerAi.readFromNBT(nbt);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("humanTypeIdx", humanType.ordinal());
		travellerAi.writeToNBT(nbt);
		super.writeEntityToNBT(nbt);
	}
	
	@SubscribeEvent
	public static void onHumanEnterChunkEventHandler(EntityEvent.EnteringChunk event) {
		if (! event.getEntity().world.isRemote) {
			Entity entity = event.getEntity();
			if (entity instanceof EntityHumanBase) {
				((EntityHumanBase) entity).humanType.onEnterChunk((EntityHumanBase) entity, 
						event.getOldChunkX(), event.getOldChunkZ(), 
						event.getNewChunkX(), event.getNewChunkZ());
			}
		}
	}
	
}
