package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

public interface IQueryStore {

    Converter getConverter();

    String getParameterPrefix();

    String getParameterPostfix();

}
