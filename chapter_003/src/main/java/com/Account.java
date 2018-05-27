package com;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Account {

    double values;
    String reqs;

    public Account(double values, String requisites) {
        this.values = values;
        this.reqs = requisites;
    }

    public double getValues() {
        return this.values;
    }


    public String getReqs() {
        return this.reqs;
    }

    boolean transfer(Account destination, double amount) {
        boolean success = false;
        if (amount > 0 && amount < this.values && destination != null) {
            success = true;
            this.values -= amount;
            destination.values += amount;
        }
        return success;
    }

    public String toString() {
        String otvet;
        otvet = "Account{" + "values=" + values + ", reqs='" + reqs + "\\" + "}";
        return otvet;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;

        return this.reqs.equals(account.reqs);
    }

    public int hashCode() {
        return this.reqs.hashCode();
    }

    public static class Convert {

        public Convert() {

        }

        //Converts array to list
        List<Integer> makeList(int[][] array) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    list.add(array[i][j]);
                }
            }
            return list;
        }


        //Converts list to array
        public int[][] makeArray(List<Integer> list, int rws) {
            Iterator<Integer> iterator = list.iterator();
            int cls = list.size() / rws + (list.size() % rws == 0 ? 0 : 1);


            int[][] array = new int[rws][cls];
            for (int i = 0; i < rws; i++) {
                for (int j = 0; j < cls; j++) {
                    if (iterator.hasNext()) {
                        array[i][j] = iterator.next();
                    } else {
                        array[i][j] = 0;
                    }
                }
            }
            return array;
        }
    }
}