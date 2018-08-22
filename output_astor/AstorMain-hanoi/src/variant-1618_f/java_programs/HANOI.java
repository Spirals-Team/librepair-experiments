package java_programs;


public class HANOI {
	public static java.util.List<java_programs.HANOI.Pair<java.lang.Integer, java.lang.Integer>> hanoi(int height, int start, int end) {
		java.util.ArrayList<java_programs.HANOI.Pair<java.lang.Integer, java.lang.Integer>> steps = new java.util.ArrayList<java_programs.HANOI.Pair<java.lang.Integer, java.lang.Integer>>();
		if (height > 0) {
			java.util.PriorityQueue<java.lang.Integer> crap_set = new java.util.PriorityQueue<java.lang.Integer>();
			crap_set.add(1);
			crap_set.add(2);
			crap_set.add(3);
			crap_set.remove(start);
			crap_set.remove(end);
			int helper = crap_set.poll();
			steps.addAll(java_programs.HANOI.hanoi((height - 1), start, helper));
			steps.add(new java_programs.HANOI.Pair<java.lang.Integer, java.lang.Integer>(start, end));
			steps.addAll(java_programs.HANOI.hanoi((height - 1), helper, end));
		}
		return steps;
	}

	public static class Pair<F, S> {
		private F first;

		private S second;

		public Pair(F first, S second) {
			this.first = first;
			this.second = second;
		}

		public void setFirst(F first) {
			this.first = first;
		}

		public void setSecond(S second) {
			this.second = second;
		}

		public F getFirst() {
			return first;
		}

		public S getSecond() {
			return second;
		}

		@java.lang.Override
		public java.lang.String toString() {
			return ((("(" + (java.lang.String.valueOf(first))) + ", ") + (java.lang.String.valueOf(second))) + ")";
		}
	}
}

