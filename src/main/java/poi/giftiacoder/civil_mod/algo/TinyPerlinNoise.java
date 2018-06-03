package poi.giftiacoder.civil_mod.algo;

import java.util.Random;

public class TinyPerlinNoise {

	private Hashs hash;
	
	public TinyPerlinNoise(long seed) {
		hash = new Hashs(new Random(seed));
	}
	
	public float getNoise(int x, int z) {
		int bx = x >> 4, bz = z >> 4;

		float fx = (float) (x & 0x0f) / 15.0F, fz = (float) (z & 0x0f) / 15.0F;
		float rx = fx + bx, rz = fz + bz;
		
		float a1 = getAffect(rx, rz, bx, bz);
		float a2 = getAffect(rx, rz, bx + 1, bz);
		float a3 = getAffect(rx, rz, bx, bz + 1);
		float a4 = getAffect(rx, rz, bx + 1, bz + 1);
		
		
		return interpolate(interpolate(a1, a2, fx), interpolate(a3, a4, fx), fz);
	}
	
	private float interpolate(float a, float b, float f) {
		//f = f * (1 - f) * 4.0F;
		//return (a + b) * f / 2.0F;
		return (a * (1 - f) + b * f) * f * (1 - f) * 4.0F;
	}
	
	private float getAffect(float x, float z, int chunkX, int chunkZ) {
		double a = ((double) hash.hash(chunkX, chunkZ) / (double) Integer.MAX_VALUE);
		double dx = x - chunkX, dz = z - chunkZ;
		
		return (float) (Math.sqrt(dx * dx + dz * dz) * a) * 0.5F;
	}
	
}
