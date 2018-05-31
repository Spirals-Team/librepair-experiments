package org.yamcs.xtce;

import java.util.Set;

public class BooleanParameterType extends BooleanDataType implements ParameterType {

    private static final long serialVersionUID = 1L;

    public BooleanParameterType(String name) {
        super(name);
    }

    /**
     * Creates a shallow copy of the parameter type
     * 
     */
    public BooleanParameterType(BooleanParameterType t) {
        super(t);
    }
    
    @Override
    public boolean hasAlarm() {
        return false;
    }

    @Override
    public String getTypeAsString() {
        return "boolean";
    }
    
    
}
