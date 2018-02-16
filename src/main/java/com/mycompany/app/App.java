package com.mycompany.app;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App
{
    public static boolean search(ArrayList<Integer> array, int e) {
        System.out.println("inside search");
        if (array == null) return false;

        for (int elt : array) {
            if (elt == e) return true;
        }
        return false;
    }

    public static ArrayList<Integer> newSearch(ArrayList<Integer> sayi1,ArrayList<Integer> sayi2,ArrayList<Integer> sayi3,int sayi4){
        for(int i = 0;i<sayi1.size();i++){
            if(sayi1.get(i)<sayi4)
                sayi3.add(sayi1.get(i));
        }
        for(int i = 0;i<sayi2.size();i++){
            if(sayi2.get(i)<sayi4)
                sayi3.add(sayi2.get(i));
        }
        int[] array = new int[sayi3.size()];
        for(int i = 0;i<sayi3.size();i++){
            array[i] = sayi3.get(i);
        }
        bubbleSort(array);
        sayi3.clear();
        for(int i = 0;i<sayi3.size();i++){
            sayi3.add(array[i]);
        }

        return sayi3;

    }

    public static void bubbleSort(int[] array) {
        for(int i = 0;i<array.length-1;i++){
            if(array[i]>array[i+1]){
                int temp = array[i];
                array[i]= array[i+1];
                array[i+1] = temp;
            }
            System.out.print("");

        }
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Hello, World");

        post("/compute", (req, res) -> {
            //System.out.println(req.queryParams("input1"));
            //System.out.println(req.queryParams("input2"));
            String input1 = req.queryParams("input1");
            java.util.Scanner sc1 = new java.util.Scanner(input1);
            sc1.useDelimiter("[;\r\n]+");
            java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
            while (sc1.hasNext())
            {
                int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
                inputList.add(value);
            }
            System.out.println(inputList);


            String input2 = req.queryParams("input2").replaceAll("\\s","");
            int input2AsInt = Integer.parseInt(input2);

            boolean result = App.search(inputList, input2AsInt);

            Map map = new HashMap();
            map.put("result", result);
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());


        get("/compute",
                (rq, rs) -> {
                    Map map = new HashMap();
                    map.put("result", "not computed yet!");
                    return new ModelAndView(map, "compute.mustache");
                },
                new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
