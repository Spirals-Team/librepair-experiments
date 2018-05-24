package mooctest;


public class IntMax {
	public int max(int x, int y, int z) {
		java.lang.String message = null;
		if (x > y) {
			y = x;
		}
		if (y > z) {
			z = y;
			print(message);
		}
		return z;
	}

	public void print(java.lang.String s) {
		java.lang.System.out.println(s.toString());
	}
}

