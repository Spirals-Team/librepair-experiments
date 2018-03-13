package ru.job4j.task;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DirectoryOfUnits {

    public List<Unit> sortUnitsDesc(List<Unit> unitsList) {

        unitsList.sort(new Comparator<Unit>() {
            @Override
            public int compare(Unit o1, Unit o2) {

                return sortUnitsByNameDescForComparator(o1, o2);
            }

        });
        return unitsList;
    }

    public List<Unit> sortUnitsAsc(List<Unit> unitsList) {

        unitsList.sort(new Comparator<Unit>() {
            @Override
            public int compare(Unit o1, Unit o2) {

                List<String> leftDecomposedName = o1.getDecomposedName();
                List<String> rightDecomposedName = o2.getDecomposedName();

                Iterator leftIterator = leftDecomposedName.listIterator();
                Iterator rightIterator = rightDecomposedName.listIterator();

                int returnValue = 0;

                while (leftIterator.hasNext() && rightIterator.hasNext()) {

                            String leftSubName = (String) leftIterator.next();
                            String rightSubName = (String) rightIterator.next();

                            if (leftSubName.compareTo(rightSubName) > 0) {
                                returnValue = -1;
                                break;
                            } else if (leftSubName.compareTo(rightSubName) < 0) {
                                returnValue = 1;
                                break;
                            } else if (!leftIterator.hasNext()) {
                                returnValue = -1;
                                break;
                            } else {
                                continue;
                            }
                }
                return returnValue;
            };
        });
        return unitsList;
    }

    int sortUnitsByNameDescForComparator(Unit o1, Unit o2) {
//        вот тут можно упростить
//        if (o1.getName().compareTo(o2.getName()) > 0) {
//            return 1;
//        } else if (o1.getName().compareTo(o2.getName()) < 0) {
//            return -1;
//        } else {
//            return 0;
//        }
        return o1.getName().compareTo(o2.getName());
    };
}