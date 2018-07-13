package ru.job4j.array;


public class BubbleSort {
    public int[] sort(int[] array){
        int swap;
        int len=array.length;
        for (int i=len-2;i>0;  i--){
            for (int j=0; j<=i; j++){
                if (array[j]>array[j+1]){
                    swap=array[j+1];
                    array[j+1]=array[j];
                    array[j]=swap;
                }
            }
        }
        return array;
    }
}
