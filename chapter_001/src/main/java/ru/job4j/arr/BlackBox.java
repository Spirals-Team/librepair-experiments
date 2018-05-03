package ru.job4j.arr;

/**
 * переопределение хешкода и и экуалс
 */
public class BlackBox {
    int varA;
    int varB;

    BlackBox(int varA, int varB) {
        this.varA = varA;
        this.varB = varB;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + varA;
        result = prime * result + varB;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BlackBox other = (BlackBox) obj;
        if (varA != other.varA) {
            return false;
        }
        if (varB != other.varB) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        BlackBox object1 = new BlackBox(5, 10);
        BlackBox object2 = new BlackBox(5, 10);
        System.out.println(object1.hashCode() + "  " + object2.hashCode());
if (object1.equals(object2)) {
    System.out.println("ебать они одинаковые");
} else {
    System.out.println("а ссалки то разные");
}
        System.out.println(object1.hashCode() + "  " + object2.hashCode());
    }
}