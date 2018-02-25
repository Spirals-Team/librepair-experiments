/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.pattern.matcher;

import java.util.List;

import spoon.SpoonException;
import spoon.pattern.Matcher;

/**
 * Chain of Matchers
 */
public class ChainOfMatchersImpl implements Matchers {
	private final Matcher firstMatcher;
	private final Matchers next;

	public static Matchers create(List<? extends Matcher> items, boolean matchAllTargets) {
		return create(items, matchAllTargets ? MATCH_ALL : MATCH_PART);
	}
	public static Matchers create(List<? extends Matcher> items, Matchers next) {
		return create(next, items.toArray(new Matcher[items.size()]));
	}
	public static Matchers create(Matchers next, Matcher... items) {
		return createFromArray(next, items, 0);
	}
	public static Matchers create(boolean matchAllTargets, Matcher... items) {
		return createFromArray(matchAllTargets ? MATCH_ALL : MATCH_PART, items, 0);
	}
	private static Matchers createFromArray(Matchers next, Matcher[] items, int idx) {
		Matcher matcher;
		while (true) {
			if (idx >= items.length) {
				return next;
			}
			matcher = items[idx];
			if (matcher != null) {
				break;
			}
			idx++;
		}
		if (next == null) {
			throw new SpoonException("The Matcher MUST NOT be null");
		}
		return new ChainOfMatchersImpl(matcher, createFromArray(next, items, idx + 1));
	}

	private ChainOfMatchersImpl(Matcher firstMatcher, Matchers next) {
		super();
		this.firstMatcher = firstMatcher;
		this.next = next;
	}

	@Override
	public TobeMatched matchAllWith(TobeMatched targets) {
		return firstMatcher.matchTargets(targets, next);
	}

	private static final TailMatcher MATCH_ALL = new TailMatcher(true);
	private static final TailMatcher MATCH_PART = new TailMatcher(false);

	private static class TailMatcher implements Matchers {
		boolean matchAllTargets;

		TailMatcher(boolean matchAllTargets) {
			super();
			this.matchAllTargets = matchAllTargets;
		}

		@Override
		public TobeMatched matchAllWith(TobeMatched targets) {
			if (matchAllTargets) {
				//we are in mode of matching of all targets. So it matches only when there is no remaining target element
				return targets.hasTargets() ? null : targets;
			}
			//all NodeMatchers matched. There might remain some unmatched target - it is OK in this context.
			return targets;
		}
	}
}
