package fr.inria.spirals.features.detector.spoon.filter;

import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.Filter;

/**
 * Created by fermadeiral
 */
public class ReturnInsideConditionalFilter implements Filter<CtReturn> {

    private CtElement ctElement;

    public ReturnInsideConditionalFilter(CtElement ctElement) {
        this.ctElement = ctElement;
    }

    @Override
    public boolean matches(CtReturn ctReturn) {
        CtElement ctElementParent = ctReturn.getParent();
        while (!(ctElementParent instanceof CtIf) && !(ctElementParent instanceof CtCase) && !(ctElementParent instanceof CtBlock)) {
            ctElementParent = ctElementParent.getParent();
        }
        if (ctElementParent == this.ctElement) {
            return true;
        }
        return false;
    }

}
