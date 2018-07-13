package ru.job4j.array;

public class RotateArray {
    int[][] rotate(int[][] array){
        int swap;
        int len=array.length;
        // меняем симметрично диагонали
        for (int i=0;i<len;i++){
            for (int j=i;j<len;j++){
                swap=array[i][j];
                array[i][j]=array[j][i];
                array[j][i] = swap;
            }
        }
        // меняем симметрично вертикали
        for (int i=0;i<len;i++){
            for (int j=0;j<=len/2-1;j++){
                swap=array[i][j];
                array[i][j]=array[i][len-1-j];
                array[i][len-1-j] = swap;
            }
        }
        return array;
    }
}
