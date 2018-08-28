package java_programs;


public class POWERSET {









	public static java.util.ArrayList<java.util.ArrayList> powerset(java.util.ArrayList arr) {
		if (!(arr.isEmpty())) {
			java.lang.Object first = arr.get(0);
			arr.remove(0);
			java.util.ArrayList rest = arr;
			java.util.ArrayList<java.util.ArrayList> rest_subsets = java_programs.POWERSET.powerset(rest);

			java.util.ArrayList<java.util.ArrayList> output = new java.util.ArrayList<java.util.ArrayList>(100);
			java.util.ArrayList to_add = new java.util.ArrayList(100);

			for (java.util.ArrayList subset : rest_subsets) {
				java.util.ArrayList r = new java.util.ArrayList();
				r.add(first);
				r.addAll(subset);
				to_add.add(r);

			}

			output.addAll(to_add);
			rest_subsets.addAll(output);





			return rest_subsets;}else { 			java.util.ArrayList empty_set = new java.util.ArrayList<java.util.ArrayList>(); 			empty_set.add(new java.util.ArrayList()); 			return empty_set;
		}
	}}