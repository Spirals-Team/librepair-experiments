package java_programs;


public class QUICKSORT {
	public static java.util.ArrayList<java.lang.Integer> quicksort(java.util.ArrayList<java.lang.Integer> arr) {
		if (arr.isEmpty()) {
			return new java.util.ArrayList<java.lang.Integer>();
		}
		java.lang.Integer pivot = arr.get(0);
		java.util.ArrayList<java.lang.Integer> lesser = new java.util.ArrayList<java.lang.Integer>();
		java.util.ArrayList<java.lang.Integer> greater = new java.util.ArrayList<java.lang.Integer>();
		for (java.lang.Integer x : arr.subList(1, arr.size())) {
			if (x <= pivot) {
				lesser.add(x);
			}else
				if (x > pivot) {
					greater.add(x);
				}

		}
		java.util.ArrayList<java.lang.Integer> middle = new java.util.ArrayList<java.lang.Integer>();
		middle.add(pivot);
		lesser = java_programs.QUICKSORT.quicksort(lesser);
		greater = java_programs.QUICKSORT.quicksort(greater);
		middle.addAll(greater);
		lesser.addAll(middle);
		return lesser;
	}
}

