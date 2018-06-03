package poi.giftiacoder.civil_mod.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class EntityUtil {

	public static void getEntitiesFromChunk(World world, int chunkX, int chunkZ, List<Entity> entitiesOut) {
		getEntitiesFromChunk(world.getChunkFromChunkCoords(chunkX, chunkZ), entitiesOut);
	}
	
	public static void getEntitiesFromChunk(Chunk chunk, List<Entity> entitiesOut) {
		for (ClassInheritanceMultiMap<Entity> map : chunk.getEntityLists()) {
			entitiesOut.addAll(map);
		}
	}
	
	public static void getEntitiesByRange(World world, int chunkX, int chunkZ, int chunkRange, List<Entity> entitiesOut) {
		for (int i = -chunkRange; i <= chunkRange; ++i) {
			for (int j = -chunkRange; j <= chunkRange; ++j) {
				getEntitiesFromChunk(world, chunkX + i, chunkZ + j, entitiesOut);
			}
		}
	}
	
	public static void getEntitiesByRange(Entity entity, int chunkRange, List<Entity> entitiesOut) {
		getEntitiesByRange(entity.world, ((int) entity.posX) >> 4, ((int) entity.posZ) >> 4, chunkRange, entitiesOut);
	}
	
	public static double getDistanceSqFromEntity(Entity e1, Entity e2) {
		double x = e1.posX - e2.posX;
		double z = e1.posZ - e2.posZ;
		return x * x + z * z;
	}
	
	public static float getDangrousFromEntity(Entity from, Entity to, float weight) {
		return weight / ((float)getDistanceSqFromEntity(from, to) + 1.0F);
	}
	
}
