package spoon.support.comparator;


public class CtLineElementComparator implements java.io.Serializable , java.util.Comparator<spoon.reflect.declaration.CtElement> {
    public int compare(spoon.reflect.declaration.CtElement o1, spoon.reflect.declaration.CtElement o2) {
        if ((o1.getPosition().isValidPosition()) == false) {
            return -1;
        }
        if ((o2.getPosition().isValidPosition()) == false) {
            return 1;
        }
        int pos1 = o1.getPosition().getSourceStart();
        int pos2 = o2.getPosition().getSourceStart();
        if (pos1 == pos2) {
            int pos3 = o1.getPosition().getSourceEnd();
            int pos4 = o2.getPosition().getSourceEnd();
            return pos3 < pos4 ? -1 : 1;
        }
        return pos1 < pos2 ? -1 : 1;
    }
}

