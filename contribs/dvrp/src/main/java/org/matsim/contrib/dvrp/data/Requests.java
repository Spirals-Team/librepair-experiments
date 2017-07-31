package org.matsim.contrib.dvrp.data;

import java.util.Comparator;

import com.google.common.base.Predicate;
import com.google.common.collect.*;

/**
 * @author michalm
 */
public class Requests {
	public static final Comparator<Request> T0_COMPARATOR = new Comparator<Request>() {
		public int compare(Request r1, Request r2) {
			return Double.compare(r1.getEarliestStartTime(), r2.getEarliestStartTime());
		}
	};

	public static final Comparator<Request> T1_COMPARATOR = new Comparator<Request>() {
		public int compare(Request r1, Request r2) {
			return Double.compare(r1.getLatestStartTime(), r2.getLatestStartTime());
		}
	};

	public static final Comparator<Request> SUBMISSION_TIME_COMPARATOR = new Comparator<Request>() {
		public int compare(Request r1, Request r2) {
			return Double.compare(r1.getSubmissionTime(), r2.getSubmissionTime());
		}
	};

	// necessary for instance when TreeSet is used to store requests
	// (TreeSet uses comparisons instead of Object.equals(Object))
	public static final Comparator<Request> ABSOLUTE_COMPARATOR = new Comparator<Request>() {
		public int compare(Request r1, Request r2) {
			return ComparisonChain.start().compare(r1.getEarliestStartTime(), r2.getEarliestStartTime())
					.compare(r1.getLatestStartTime(), r2.getLatestStartTime())
					.compare(r1.getSubmissionTime(), r2.getSubmissionTime()).compare(r1.getId(), r2.getId()).result();
		}
	};

	public static class IsUrgentPredicate implements Predicate<Request> {
		private double now;

		public IsUrgentPredicate(double now) {
			this.now = now;
		}

		public boolean apply(Request vehicle) {
			return isUrgent(vehicle, now);
		}
	}

	public static final boolean isUrgent(Request request, double now) {
		return request.getEarliestStartTime() <= now;
	}

	public static int countRequests(Iterable<? extends Request> requests, Predicate<Request> predicate) {
		return Iterables.size(Iterables.filter(requests, predicate));
	}

	public static <R extends Request> Iterable<R> filterRequests(Iterable<R> requests, Predicate<Request> predicate) {
		return Iterables.filter(requests, predicate);
	}
}
