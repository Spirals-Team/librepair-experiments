package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;

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
public class ImmutableSubstringSimilarity implements SubstringSimilarity, Serializable {

	private static final long serialVersionUID = -3314129604836270650L;
	
	private final int positionInLeftString;
	private final int positionInRightString;
	private final String similarSubstring;
	
	public ImmutableSubstringSimilarity(int positionInLeftString, int positionInRightString, String similarSubstring) {
		super();
		this.positionInLeftString = positionInLeftString;
		this.positionInRightString = positionInRightString;
		this.similarSubstring = similarSubstring;
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
	public String getSimilarSubstring() {
		return similarSubstring;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((similarSubstring == null) ? 0 : similarSubstring.hashCode());
		result = prime * result + positionInLeftString;
		result = prime * result + positionInRightString;
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
		ImmutableSubstringSimilarity other = (ImmutableSubstringSimilarity) obj;
		if (similarSubstring == null) {
			if (other.similarSubstring != null)
				return false;
		} else if (!similarSubstring.equals(other.similarSubstring))
			return false;
		return positionInLeftString == other.positionInLeftString && positionInRightString == other.positionInRightString;
	}

	@Override
	public String toString() {
		return "ImmutableSubstringCommon [positionInLeftString=" + positionInLeftString + ", positionInRightString="
				+ positionInRightString + ", similarSubstring='" + similarSubstring + "']";
	}
}
