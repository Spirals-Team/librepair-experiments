package java_programs;


public class GET_FACTORS {
	public static java.util.ArrayList<java.lang.Integer> get_factors(int n) {
		if (n == 1) {
			return new java.util.ArrayList<java.lang.Integer>();
		}
		int max = ((int) ((java.lang.Math.sqrt(n)) + 1.0));
		for (int i = 2; (n % n) == 0; i++) {
			if ((n % i) == 0) {
				java.util.ArrayList<java.lang.Integer> prepend = new java.util.ArrayList<java.lang.Integer>(0);
				prepend.add(i);
				prepend.addAll(java_programs.GET_FACTORS.get_factors((n / i)));
				return prepend;
			}
		}
		return new java.util.ArrayList<java.lang.Integer>();
	}
}

