package org.orienteer.core.method.filters;

import org.orienteer.core.method.IMethodFilter;

/**
 * 
 * OFilter stub for pure string filters
 *
 */
public abstract class AbstractStringFilter implements IMethodFilter{

	protected String filterData;
	
	@Override
	public IMethodFilter setFilterData(String filterData) {
		this.filterData = filterData;
		return this;
	}
}
