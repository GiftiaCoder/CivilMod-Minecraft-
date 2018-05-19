package poi.giftiacoder.civil_mod.algo;

import java.util.ArrayList;
import java.util.List;

public class PerlinNoise {
	
	public static float perlinNoise(float x, float z, float persist, float octave) {
		
		float v = 0.0F;
		
		float f = 1.0F;
		float a = 1.0F;
		float t = 0.0F;
		
		for (int i = 0; i < octave; ++i) {
			v += perlinNoise(x * f, z * f) * a;
			
			t += a;
			a *= persist;
			
			f *= 2.0F;
		}
		
		return v / t;
	}
	
	public static float perlinNoise(float x, float z) {
		int ix = (int)x, iz = (int)z;
		if (x < 0) --ix;
		if (z < 0) --iz;
		
		float fx = x - (float)ix, fz = z - (float)iz;
		
		float a1 = affect(x, z, ix, iz);
		float a2 = affect(x, z, ix + 1, iz);
		float a3 = affect(x, z, ix, iz + 1);
		float a4 = affect(x, z, ix + 1, iz + 1);
		
		float a = interpolate(interpolate(a1, a2, fx), interpolate(a3, a4, fx), fz);
		return a;
	}
	
	// 6*(x^5)-15*(x^4)+10*(x^3)
	private static float interpolate(float a, float b, float f) {
		//double ft = (double) ((1.0D - Math.cos(f * Math.PI)) * 0.5);
		//return b * ft + a * (1.0D - ft);
		
		//return f * b + (1.0D - f) * a;
		
		float fp3 = (float) Math.pow(f, 3);
		float fp4 = fp3 * f;
		float fp5 = fp4 * f;
		float fp = 6 * fp5 - 15 * fp4 + 10 * fp3;
		return b * fp + (1.0F - fp) * a;
	}
	
	private static float affect(float x, float z, int ox, int oz) {
		float dx = (x - (float)ox), dz = (z - (float)oz);
		
		float vx = noise((int)ox, (int)oz * 17921), vz = noise((int)ox * 30577, (int)oz);
		
		float a = (dx) * (vx) + (dz) * (vz);
		return (a + 2.0F) / 4.0F;
	}
	
	private static float noise(int x, int z) {
		int n = (x * 5) ^ (z * 11) ^ (x * 23) ^ (z * 31);//x + z * 71;
		n = (n * 757 + 12011) * 4007;
		int l = 0x7FFFFFFF & n;
		double d = (double)l / 1073741823.5D;
		return (float)(d - 1.0F);
	}
}
