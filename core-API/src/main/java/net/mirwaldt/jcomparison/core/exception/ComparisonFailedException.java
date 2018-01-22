package net.mirwaldt.jcomparison.core.exception;

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
public class ComparisonFailedException extends Exception {

	private final Object leftObject;
	private final Object rightObject;

	public ComparisonFailedException(Object leftObject, Object rightObject) {
		this.leftObject = leftObject;
		this.rightObject = rightObject;
	}

	public ComparisonFailedException(String message, Object leftObject, Object rightObject) {
		super(message);
		this.leftObject = leftObject;
		this.rightObject = rightObject;
	}

	public ComparisonFailedException(String message, Throwable cause, Object leftObject, Object rightObject) {
		super(message, cause);
		this.leftObject = leftObject;
		this.rightObject = rightObject;
	}

	public ComparisonFailedException(Throwable cause, Object leftObject, Object rightObject) {
		super(cause);
		this.leftObject = leftObject;
		this.rightObject = rightObject;
	}

	public ComparisonFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object leftObject, Object rightObject) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.leftObject = leftObject;
		this.rightObject = rightObject;
	}

	public Object getLeftObject() {
		return leftObject;
	}

	public Object getRightObject() {
		return rightObject;
	}
}
