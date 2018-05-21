package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.parameter.ParameterType;

/**
 * Factory interface for all parameter types
 * @see ParameterType
 * @see Parameter
 */
public interface ParameterFactory {

    /**
     * creates parameter in desired type
     *
     * @param parameterType type of the new parameter
     * @return created parameter object
     */
    Parameter createParameter(ParameterType parameterType);
}
