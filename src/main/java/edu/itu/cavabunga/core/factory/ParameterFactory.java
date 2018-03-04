package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.parameter.ParameterType;

public interface ParameterFactory {
    Parameter createParameter(ParameterType parameterType);
}
