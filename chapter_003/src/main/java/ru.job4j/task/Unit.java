package ru.job4j.task;

import java.util.ArrayList;
import java.util.List;

public class Unit {

    private String name;

    private List<String> decomposedName = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public List<String> getDecomposedName() {
        return decomposedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Unit unit = (Unit) o;

        return name != null ? name.equals(unit.name) : unit.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public Unit(String name) {
        this.name = name;               String nameForSplit = this.name.replace(
                new String("\\").toCharArray()[0],
                new String(";").toCharArray()[0]);

        String regex = new String(";");

        String[] arrayOfDivAndSubDiv = nameForSplit.split(regex);

        for (int ind = 0; ind < arrayOfDivAndSubDiv.length; ind++) {
            decomposedName.add(arrayOfDivAndSubDiv[ind]);
        }
    }
}
