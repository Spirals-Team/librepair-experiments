package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;

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
//TODO: rename from ImmutableSubstringDiff to ImmutableSubstringDifference
public class ImmutableSubstringDiff implements SubstringDifference, Serializable {

	private static final long serialVersionUID = -2095717104834925654L;
	
	private final int positionInLeftString;
	private final String substringInLeftString;
	
	private final int positionInRightString;	
	private final String substringInRightString;
	
	public ImmutableSubstringDiff(int positionInLeftString, int positionInRightString,
			String substringInLeftString, String substringInRightString) {
		super();
		this.positionInLeftString = positionInLeftString;
		this.positionInRightString = positionInRightString;
		this.substringInLeftString = substringInLeftString;
		this.substringInRightString = substringInRightString;
	}

	@Override
	public int getPositionInLeftString() {
		return positionInLeftString;
	}

	@Override
	public int getPositionInRightString() {
		return positionInRightString;
	}

	@Override
	public String getSubstringInLeftString() {
		return substringInLeftString;
	}

	@Override
	public String getSubstringInRightString() {
		return substringInRightString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + positionInLeftString;
		result = prime * result + positionInRightString;
		result = prime * result + ((substringInLeftString == null) ? 0 : substringInLeftString.hashCode());
		result = prime * result + ((substringInRightString == null) ? 0 : substringInRightString.hashCode());
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
		ImmutableSubstringDiff other = (ImmutableSubstringDiff) obj;
		if (positionInLeftString != other.positionInLeftString)
			return false;
		if (positionInRightString != other.positionInRightString)
			return false;
		if (substringInLeftString == null) {
			if (other.substringInLeftString != null)
				return false;
		} else if (!substringInLeftString.equals(other.substringInLeftString))
			return false;
		if (substringInRightString == null) {
			if (other.substringInRightString != null)
				return false;
		} else if (!substringInRightString.equals(other.substringInRightString))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImmutableSubstringDiff [positionInLeftString=" + positionInLeftString + ", positionInRightString="
				+ positionInRightString + ", substringInLeftString='" + substringInLeftString + "'"
				+ ", substringInRightString='" + substringInRightString + "']";
	}
	
}
