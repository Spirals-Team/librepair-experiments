package com.thekoalas.koalas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author medardt
 */
public abstract class Koalas {

    public static void main(String[] args) throws IOException {
        //Tests Naiqi/Benjamin, pour s'assurer que tout fonctionne correctement !
        //Créer un dataset à partir de colonnes.

        ArrayList<Column> list = new ArrayList<>();
        ArrayList<Integer> intList = new ArrayList<>();
        list.add(new Column("A", intList));
        DataFrame data1 = new DataFrame(list);
        System.out.println(data1);
        //Créer un dataset à partir de noms et de données.
        ArrayList<String> names = new ArrayList<>();
        names.add("A");
        names.add("B");
        names.add("C");

        ArrayList<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);
        col1.add(-1);
        col1.add(5);
        col1.add(6);
        col1.add(-1);
        ArrayList<Integer> col2 = new ArrayList<>();
        col2.add(2);
        col2.add(2);
        col2.add(2);
        col2.add(2);
        col2.add(2);
        col2.add(2);
        col2.add(1);
        ArrayList<String> col3 = new ArrayList<>();
        col3.add("Coucou");
        col3.add("Coucou");
        col3.add("Coucou");
        col3.add("Coucou");
        col3.add("Coucou");
        col3.add("Coucou");
        col3.add("Coucou");
        List<List<? extends Comparable>> l2 = new ArrayList<>();
        l2.add(col1);
        l2.add(col2);
        l2.add(col3);

        DataFrame data = new DataFrame(names, l2);
        DataFrame data3 = data.getLineSubset(3);
        ArrayList<String> namesR = new ArrayList<>();
        namesR.add("A");
        namesR.add("B");
        namesR.add("A");
        System.out.println(data3.getColumnSubset(namesR).display());
        System.out.println("Hello koalas !");
        System.out.println(data);
        data.statistics();
        
        
        DataFrame d = new DataFrame("test.csv");
        System.out.println(d.groupByAggregate("country", "max"));
        System.out.println(data.groupByAggregate("C", "avg"));
    }

    public static String hello() {
        return "Hello :D";
    }

}
