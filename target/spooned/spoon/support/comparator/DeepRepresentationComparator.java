package spoon.support.comparator;


public class DeepRepresentationComparator implements java.io.Serializable , java.util.Comparator<spoon.reflect.declaration.CtElement> {
    @java.lang.Override
    public int compare(spoon.reflect.declaration.CtElement o1, spoon.reflect.declaration.CtElement o2) {
        if ((o1.getPosition().isValidPosition()) == false) {
            return 1;
        }
        if ((o2.getPosition().isValidPosition()) == false) {
            return -1;
        }
        java.lang.String current = getDeepRepresentation(o1);
        java.lang.String other = getDeepRepresentation(o2);
        if (((current.length()) <= 0) || ((other.length()) <= 0)) {
            throw new java.lang.ClassCastException("Unable to compare elements");
        }
        return current.compareTo(other);
    }

    private java.lang.String getDeepRepresentation(spoon.reflect.declaration.CtElement elem) {
        return elem.toString();
    }
}

