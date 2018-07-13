package ru.job4j.array;

public class Turn {
    public int[] back(int[] array){
        int len=array.length;
        int swap;
        for (int i=1; i<=len/2; i++){
            swap=array[i-1];
            array[i-1] =array[len-i];
            array[len-i]=swap;
        }
        return array;
    }
}
