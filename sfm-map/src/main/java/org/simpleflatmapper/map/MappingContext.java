package org.simpleflatmapper.map;

import org.simpleflatmapper.map.context.MappingContextFactory;

public class MappingContext<S> {

    public static final MappingContext EMPTY_CONTEXT = new MappingContext();

    public static final MappingContextFactory EMPTY_FACTORY = new MappingContextFactory() {
        @Override
        public MappingContext newContext() {
            return EMPTY_CONTEXT;
        }
    };

    public boolean broke(S source) {
        return true;
    }

    public void markAsBroken() {
    }

    public <T> T context(int i) {
        return null;
    }

    public void setCurrentValue(int i, Object value) {

    }

    public Object getCurrentValue(int i) {
        return null;
    }

}
