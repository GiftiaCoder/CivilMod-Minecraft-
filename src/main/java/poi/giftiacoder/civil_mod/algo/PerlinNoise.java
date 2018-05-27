package poi.giftiacoder.civil_mod.algo;

import java.util.Random;

public class PerlinNoise {
	
	private float persist;
	private int octave;
	
	private Hashs hash;
	
	public PerlinNoise(float persist, int octave, long seed) {
		this.persist = persist;
		this.octave = octave;
		this.hash = new Hashs(new Random(seed));
	}
	
	public float perlinNoise(float x, float z) {
		float v = 0.0F;
		
		float f = 1.0F;
		float a = 1.0F;
		float t = 0.0F;
		
		for (int i = 0; i < octave; ++i) {
			v += perlinNoiseSingle(x * f, z * f) * a;
			
			t += a;
			a *= persist;
			
			f *= 2.0F;
		}
		
		return v / t;
	}
	
	private float perlinNoiseSingle(float x, float z) {
		int ix = (int)x, iz = (int)z;
		if (x < 0) --ix;
		if (z < 0) --iz;
		
		float fx = x - (float)ix, fz = z - (float)iz;
		
		float a1 = affect(x, z, ix, iz);
		float a2 = affect(x, z, ix + 1, iz);
		float a3 = affect(x, z, ix, iz + 1);
		float a4 = affect(x, z, ix + 1, iz + 1);
		//System.out.printf("perlin affect: %f, %f, %f, %f\n", a1, a2, a3, a4);
		
		float a = interpolate(interpolate(a1, a2, fx), interpolate(a3, a4, fx), fz);
		return a;
	}
	
	// 6*(x^5)-15*(x^4)+10*(x^3)
	private float interpolate(float a, float b, float f) {
		//double ft = (double) ((1.0D - Math.cos(f * Math.PI)) * 0.5);
		//return (float) (b * ft + a * (1.0D - ft));
		
		//return f * b + (1.0D - f) * a;
		
		float fp3 = (float) Math.pow(f, 3);
		float fp4 = fp3 * f;
		float fp5 = fp4 * f;
		float fp = 6 * fp5 - 15 * fp4 + 10 * fp3;
		return b * fp + (1.0F - fp) * a;
	}
	
	private float affect(float x, float z, int ox, int oz) {
		float dx = (x - (float)ox), dz = (z - (float)oz);
		
		float vx = noise(ox, oz * 17921), vz = noise(ox * 30577, oz);
		
		float a = (dx * vx) + (dz * vz);
		return (a + 2.0F) / 4.0F;
	}
	
	private float noise(int x, int z) {
		double d = (double)hash.hash(x, z) / (double)Integer.MAX_VALUE;
		return (float)((d * 2.0D) - 1.0D);
	}
	
}
