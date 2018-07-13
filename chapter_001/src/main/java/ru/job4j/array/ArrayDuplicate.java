package ru.job4j.array;

import java.util.Arrays;

public class ArrayDuplicate {
    public String[] remove(String[] array){
        int arcut=array.length;
        String swap;
        for (int i=0; i<arcut ;i++){
            for(int j=i+1;j<arcut;j++){
                if (array[i].equals(array[j])){
                    swap=array[j];
                    array[j]=array[arcut-1];
                    arcut--;
                    j--;
                }
            }
        }
        return Arrays.copyOf(array,arcut);
    }
}
