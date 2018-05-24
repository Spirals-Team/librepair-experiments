package mooctest;


public class IntMax {
	public int max(int x, int y, int z) {
		java.lang.Integer i = null;
		if (x > y) {
			y = x;
		}
		if (y > z)
			z = y;

		return z;
	}
}

