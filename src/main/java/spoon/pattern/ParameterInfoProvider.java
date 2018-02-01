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
package spoon.pattern;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides {@link ParameterInfo} - declaration of parameter
 */
public class ParameterInfoProvider {
	private final Map<String, ParameterInfo> parameterInfos = new HashMap<>();
	private ParameterInfoProvider parentParameterInfoProvider;
	private ValueConvertor defaultValueConvertor;

	public ParameterInfoProvider(ValueConvertor defaultValueConvertor) {
		this.defaultValueConvertor = defaultValueConvertor;
	}

	public ParameterInfoProvider(ParameterInfoProvider parent) {
		this.parentParameterInfoProvider = parent;
	}

	public ParameterInfo getParameterInfo(String parameterName, boolean createIfNotExist) {
		ParameterInfo pi = parameterInfos.get(parameterName);
		if (pi == null) {
			pi = new ParameterInfo(getDefaultValueConvertor(), parameterName);
			parameterInfos.put(parameterName, pi);
		}
		return pi;
	}

	public boolean hasParameterInfo(String parameterName) {
		return parameterInfos.containsKey(parameterName);
	}

	public ValueConvertor getDefaultValueConvertor() {
		if (defaultValueConvertor == null) {
			return parentParameterInfoProvider.getDefaultValueConvertor();
		}
		return defaultValueConvertor;
	}

	public void setDefaultValueConvertor(ValueConvertor defaultValueConvertor) {
		this.defaultValueConvertor = defaultValueConvertor;
	}
}
