package spoon.reflect.visitor.filter;


public class DirectReferenceFilter<T extends spoon.reflect.reference.CtReference> extends spoon.reflect.visitor.filter.AbstractFilter<T> {
    spoon.reflect.reference.CtReference reference;

    @java.lang.SuppressWarnings("unchecked")
    public DirectReferenceFilter(spoon.reflect.reference.CtReference reference) {
        super(((java.lang.Class<T>) (reference.getClass())));
        this.reference = reference;
    }

    public boolean matches(T reference) {
        if ((super.matches(reference)) == false) {
            return false;
        }
        return this.reference.equals(reference);
    }
}

