package net.mirwaldt.jcomparison.core.pair.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;

import java.io.Serializable;

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
public class ImmutablePair<ValueType> implements Pair<ValueType>, Serializable {

	private static final long serialVersionUID = 501899905221121428L;
	
	private final ValueType leftValue;
	private final ValueType rightValue;
	
	public ImmutablePair(ValueType leftValue, ValueType rightValue) {
		super();
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}

	@Override
	public ValueType getLeft() {
		return leftValue;
	}

	@Override
	public ValueType getRight() {
		return rightValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((leftValue == null) ? 0 : leftValue.hashCode());
		result = prime * result + ((rightValue == null) ? 0 : rightValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImmutablePair other = (ImmutablePair) obj;
		if (leftValue == null) {
			if (other.leftValue != null)
				return false;
		} else if (!leftValue.equals(other.leftValue))
			return false;
		if (rightValue == null) {
			if (other.rightValue != null)
				return false;
		} else if (!rightValue.equals(other.rightValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(leftValue=" + leftValue + ",rightValue=" + rightValue + ")";
	}

}
