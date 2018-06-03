package poi.giftiacoder.civil_mod.entity.civil.human;

import net.minecraft.world.chunk.Chunk;

public enum HumanType {

	TRAVELLER {
		@Override
		public HumanType update(EntityHumanBase human) {
			human.travellerAi.updateAi();
			// TODO
			return TRAVELLER;
		}

		@Override
		public void onEnterChunk(EntityHumanBase human, int oldChunkX, int oldChunkZ, int newChunkX, int newChunkZ) {
			human.travellerAi.onEnterChunk(oldChunkX, oldChunkZ, newChunkX, newChunkZ);
		}
	};
	
	public abstract HumanType update(EntityHumanBase human);
	
	public abstract void onEnterChunk(EntityHumanBase human, int oldChunkX, int oldChunkZ, int newChunkX, int newChunkZ);
	
}
