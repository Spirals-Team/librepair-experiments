package spoon.support.comparator;


public class QualifiedNameComparator implements java.io.Serializable , java.util.Comparator<spoon.reflect.declaration.CtElement> {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public int compare(spoon.reflect.declaration.CtElement o1, spoon.reflect.declaration.CtElement o2) {
        try {
            if ((o1 instanceof spoon.reflect.declaration.CtTypeInformation) && (o2 instanceof spoon.reflect.declaration.CtTypeInformation)) {
                return ((spoon.reflect.declaration.CtTypeInformation) (o1)).getQualifiedName().compareTo(((spoon.reflect.declaration.CtTypeInformation) (o2)).getQualifiedName());
            }
            if ((o1 instanceof spoon.reflect.declaration.CtPackage) && (o2 instanceof spoon.reflect.declaration.CtPackage)) {
                return ((spoon.reflect.declaration.CtPackage) (o1)).getQualifiedName().compareTo(((spoon.reflect.declaration.CtPackage) (o2)).getQualifiedName());
            }
            if ((o1 instanceof spoon.reflect.reference.CtReference) && (o2 instanceof spoon.reflect.reference.CtReference)) {
                return ((spoon.reflect.reference.CtReference) (o1)).getSimpleName().compareTo(((spoon.reflect.reference.CtReference) (o2)).getSimpleName());
            }
            if ((o1 instanceof spoon.reflect.declaration.CtNamedElement) && (o2 instanceof spoon.reflect.declaration.CtNamedElement)) {
                return ((spoon.reflect.declaration.CtNamedElement) (o1)).getSimpleName().compareTo(((spoon.reflect.declaration.CtNamedElement) (o2)).getSimpleName());
            }
            throw new java.lang.IllegalArgumentException();
        } catch (java.lang.NullPointerException e) {
            return -1;
        }
    }
}

