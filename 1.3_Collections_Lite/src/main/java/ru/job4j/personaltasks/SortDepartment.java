package ru.job4j.personaltasks;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class sort department.
 */
public class SortDepartment {

    /**
     * Sort by Ascending.
     *
     * @param department Array for sort
     * @return sorted array
     */
    public String[] sortAscending(String[] department) {
        String[] result = this.add(department);
        return result;
    }

    /**
     * Sort by descending.
     *
     * @param department Array for sort
     * @return sorted array
     */
    public String[] sortDescending(String[] department) {
        String[] temp = this.add(department);
        Set<String> set = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result = 0;
                if (o1.charAt(1) != o2.charAt(1)) {
                    result = o2.compareTo(o1);
                } else {
                    result = o1.length() > o2.length() ? 1 : -1;
                }
                return result;
            }
        });
        for (String str : temp) {
            set.add(str);
        }
        String[] result = set.toArray(new String[set.size()]);
        return result;
    }

    /**
     * Adding missing elements.
     * @param departments array for add
     * @return full array
     */
    private String[] add(String[] departments) {
        Set<String> temp = new TreeSet<>();
        String value;
        for (int i = 0; i < departments.length; i++) {

            if (departments[i].contains("/")) {
                value = departments[i].substring(0, departments[i].lastIndexOf("/"));
                temp.add(value);
            }
            temp.add(departments[i]);
        }
        String[] result = new String[temp.size()];
        result = temp.toArray(result);
        return result;
    }
}
