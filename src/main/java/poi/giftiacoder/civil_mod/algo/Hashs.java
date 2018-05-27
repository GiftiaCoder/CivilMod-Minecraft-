package poi.giftiacoder.civil_mod.algo;

import java.util.Random;

public class Hashs {
	
	private long a, b, c, d, e;
	
	public Hashs(Random rand) {
		a = Primzahl.PRIMZAHLS[rand.nextInt(Primzahl.PRIMZAHLS.length)];
		b = Primzahl.PRIMZAHLS[rand.nextInt(Primzahl.PRIMZAHLS.length)];
		c = Primzahl.PRIMZAHLS[rand.nextInt(Primzahl.PRIMZAHLS.length)];
		d = Primzahl.PRIMZAHLS[rand.nextInt(Primzahl.PRIMZAHLS.length)];
		e = Primzahl.PRIMZAHLS[rand.nextInt(Primzahl.PRIMZAHLS.length)];
	}
	
	/**
	 * return the hash code of (x) that between 0-Integer.MAX_VALUE
	 */
	public int hash(int x) {
		//n = (n * 757 + 12011) * 4007;
		//n = (n * 32369 + 104009) * 57791;
		long n = x;
		n = (((n * a + b) * c + d) * e);
		//long l = 0x7FFFFFFF & n;
		//return (int)l;
		return (int) (0x07FFFFFFFL & n);
	}
	/**
	 * return the hash code of (x, z) that between 0-Integer.MAX_VALUE
	 */
	public int hash(int x, int z) {
		int n = ((x * 1153) << 11) ^ ((z * 1031) << 23) ^ (x * 23) ^ (z * 31);//x + z * 71;
		return hash(n);
		//int n = x + z * 71;
		//n *= 40751;
		//n = ~n + (n << 15);
		//n = n ^ (n >> 12);
		//n = n + (n << 2);
		//n = n ^ (n >> 4);
		//n = n * 2057;
		//n = n ^ (n >> 16);
		//return n;
	}
	
}
