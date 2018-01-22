package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparisonResult;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.object.api.ObjectComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ComparisonResults {
	@SuppressWarnings("rawtypes")
	private static final ComparisonResult EMPTY_COMPARISON_RESULT = new ComparisonResult() {
		@Override
		public String toString() {
			return "EMPTY_COMPARISON_RESULT";
		}
	};
	
	@SuppressWarnings("unchecked")
	public static <T, U, V> ComparisonResult<T, U, V> emptyComparisonResult() {
		return (ComparisonResult<T, U, V>) EMPTY_COMPARISON_RESULT;
	}

	@SuppressWarnings("rawtypes")
	private static final ComparisonResult SKIP_COMPARISON_RESULT = new ComparisonResult() {
		@Override
		public String toString() {
			return "SKIP_COMPARISON_RESULT";
		}
	};

	@SuppressWarnings("unchecked")
	public static <T, U, V> ComparisonResult<T, U, V> skipComparisonResult() {
		return (ComparisonResult<T, U, V>) SKIP_COMPARISON_RESULT;
	}

	@SuppressWarnings("rawtypes")
	private static final ComparisonResult STOP_COMPARISON_RESULT = new ComparisonResult() {
		@Override
		public String toString() {
			return "STOP_COMPARISON_RESULT";
		}
	};

	@SuppressWarnings("unchecked")
	public static <T, U, V> ComparisonResult<T, U, V> stopComparisonResult() {
		return (ComparisonResult<T, U, V>) STOP_COMPARISON_RESULT;
	}

	public static final SetComparisonResult EMPTY_SET_COMPARISON_RESULT = new SetComparisonResult() {
		@Override
		public String toString() {
			return "EMPTY_SET_COMPARISON_RESULT";
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> SetComparisonResult<T> emptySetComparisonResult() {
		return (SetComparisonResult<T>) EMPTY_SET_COMPARISON_RESULT;
	}

	public static final UniquesListComparisonResult EMPTY_UNIQUES_LIST_COMPARISON_RESULT = new UniquesListComparisonResult() {
		@Override
		public String toString() {
			return "EMPTY_UNIQUES_LIST_COMPARISON_RESULT";
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> UniquesListComparisonResult<T> emptyUniquesListComparisonResult() {
		return (UniquesListComparisonResult<T>) EMPTY_UNIQUES_LIST_COMPARISON_RESULT;
	}

	public static final DuplicatesListComparisonResult EMPTY_DUPLICATES_LIST_COMPARISON_RESULT = new DuplicatesListComparisonResult() {
		@Override
		public String toString() {
			return "EMPTY_DUPLICATES_LIST_COMPARISON_RESULT";
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> DuplicatesListComparisonResult<T> emptyDuplicatesListComparisonResult() {
		return (DuplicatesListComparisonResult<T>) EMPTY_DUPLICATES_LIST_COMPARISON_RESULT;
	}

	public static final MapComparisonResult EMPTY_MAP_COMPARISON_RESULT = new MapComparisonResult() {
		@Override
		public String toString() {
			return "EMPTY_MAP_COMPARISON_RESULT";
		}
	};

	@SuppressWarnings("unchecked")
	public static <K,V> MapComparisonResult<K,V> emptyMapComparisonResult() {
		return (MapComparisonResult<K,V>) EMPTY_MAP_COMPARISON_RESULT;
	}

	public static final SubstringComparisonResult EMPTY_SUBSTRING_COMPARISON_RESULT = new SubstringComparisonResult() {
		@Override
		public String toString() {
			return "EMPTY_SUBSTRING_COMPARISON_RESULT";
		}
	};

	public static final ObjectComparisonResult EMPTY_OBJECT_COMPARISON_RESULT = new ObjectComparisonResult() {
		@Override
		public String toString() {
			return "EMPTY_OBJECT_COMPARISON_RESULT";
		}
	};

	public static final ArrayComparisonResult EMPTY_OBJECT_ARRAY_COMPARISON_RESULT = new ArrayComparisonResult() {
		@Override
		public String toString() {
			return "EMPTY_OBJECT_ARRAY_COMPARISON_RESULT";
		}
	};
}
