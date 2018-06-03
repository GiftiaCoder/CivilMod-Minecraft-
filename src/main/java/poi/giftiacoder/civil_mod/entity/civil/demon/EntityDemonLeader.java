package poi.giftiacoder.civil_mod.entity.civil.demon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class EntityDemonLeader extends EntityDemonBase {
	
	private static final List<Entity> EMPTY_SIGHT_LIST = new ArrayList<>();
	
	private static enum AIStratage {
		FINDFRIENDS {
			@Override
			public AIStratage updateEntityAI(EntityDemonLeader entity) {
				// TODO Auto-generated method stub
				return null;
			}
		}/*, FINDFRIENDS2, ATTACKTOWARD, WITHDRAW, AHEADONENEMY*/;
		
		public abstract AIStratage updateEntityAI(EntityDemonLeader entity);
	}
	
	private AIStratage aiStratage = AIStratage.FINDFRIENDS;
	
	private List<Entity> entityInSight = EMPTY_SIGHT_LIST;
	
	private Set<EntityDemonBerserker> berserkerList = new HashSet<>();
	private Set<EntityDemonInfector> infectorList = new HashSet<>();
	private Set<EntityDemonRider> riderList = new HashSet<>();
	
	public EntityDemonLeader(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		aiStratage = aiStratage.updateEntityAI(this);
	}
	
	private void updateSight() {
		int chunkX = (int) posX >> 4;
		int chunkZ = (int) posZ >> 4;
		
		List<Entity> sightList = new ArrayList<>();
		
		getEntityFromChunk(sightList, chunkX, chunkZ);
		
		getEntityFromChunk(sightList, chunkX, chunkZ - 1);
		getEntityFromChunk(sightList, chunkX, chunkZ + 1);
		getEntityFromChunk(sightList, chunkX - 1, chunkZ);
		getEntityFromChunk(sightList, chunkX + 1, chunkZ);
		
		getEntityFromChunk(sightList, chunkX - 1, chunkZ - 1);
		getEntityFromChunk(sightList, chunkX - 1, chunkZ + 1);
		getEntityFromChunk(sightList, chunkX + 1, chunkZ - 1);
		getEntityFromChunk(sightList, chunkX + 1, chunkZ + 1);
		
		getEntityFromChunk(sightList, chunkX - 1, chunkZ - 2);
		getEntityFromChunk(sightList, chunkX, chunkZ - 2);
		getEntityFromChunk(sightList, chunkX + 1, chunkZ - 2);
		
		getEntityFromChunk(sightList, chunkX - 1, chunkZ + 2);
		getEntityFromChunk(sightList, chunkX, chunkZ + 2);
		getEntityFromChunk(sightList, chunkX + 1, chunkZ + 2);
		
		getEntityFromChunk(sightList, chunkX - 2, chunkZ - 1);
		getEntityFromChunk(sightList, chunkX - 2, chunkZ);
		getEntityFromChunk(sightList, chunkX - 2, chunkZ + 1);
		
		getEntityFromChunk(sightList, chunkX + 2, chunkZ - 1);
		getEntityFromChunk(sightList, chunkX + 2, chunkZ);
		getEntityFromChunk(sightList, chunkX + 2, chunkZ + 1);
		
		entityInSight = sightList;
	}
	private void getEntityFromChunk(List<Entity> sightList, int chunkX, int chunkZ) {
		Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
		for (ClassInheritanceMultiMap<Entity> map : chunk.getEntityLists()) {
			sightList.addAll(map);
		}
	}
	private void iterateSightList() {
		for (Entity entity : entityInSight) {
			if (entity instanceof EntityDemonBase) {
				
				if (entity instanceof EntityDemonBerserker) {
					// TODO
				}
				else if (entity instanceof EntityDemonInfector) {
					// TODO
				}
				else if (entity instanceof EntityDemonRider) {
					// TODO
				} 
				else { // entity instanceof ENtityDemonTitan
					// TODO
				}
			}
			else {
				// TODO if is human noncombatant
				// TODO if is human combatant
				// TODO if is steve
			}
		}
	}
	
}
