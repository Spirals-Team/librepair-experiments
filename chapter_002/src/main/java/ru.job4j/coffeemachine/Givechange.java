package ru.job4j.coffeemachine;

public class Givechange {

    int value;
    int price;
    int[] change = {1000, 500, 100, 50, 10, 5, 1};

    Givechange(int value, int price) {
        this.value = value;
        this.price = price;
    }

    int[] getChangeByCoffee() {
        int[] returnChange = new int[15];
        recursiveChangeVave(this.value - this.price, 0, 0, returnChange);
        return returnChange;
    }

    boolean recursiveChangeVave(int returnSumm, int indNominal, int indRecursion, int[] returnChange) {
        boolean result = false;
        if (returnSumm == 0) {
            result = true;
        } else {
            int nominal = this.change[indNominal];
            if (returnSumm - nominal >= 0) {
                returnSumm = returnSumm - nominal;
                returnChange[indRecursion++] = nominal;
            } else {
                indNominal++;
            }
            return recursiveChangeVave(returnSumm, indNominal, indRecursion, returnChange);
        }
        return result;
    }
}