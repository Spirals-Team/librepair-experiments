package ru.job4j.dvumernmass;

public class MetodReshenia {

    public int returnresult(int[][] arr) {
        int result = 0;
        boolean exit = true;
        int kvadrat = 0;


        do {
            int[][] arrTemp = new int[arr.length - kvadrat][arr[1].length - kvadrat];
            for (int i = 0 + kvadrat, k = 0; i < arr.length - kvadrat; i++, k++) {
                System.out.println(" ");
                for (int j = 0 + kvadrat, v = 0; j < arr[i].length - kvadrat; j++, v++) {
                    arrTemp[k][v] = arr[i][j];
                    System.out.print(arrTemp[k][v] + " ");
                }
            }

            result += (getMax(arrTemp) - getMin(arrTemp));
            System.out.println("result+= (" + getMax(arrTemp) + " - " + getMin(arrTemp) + ") = " + result);
            kvadrat++;
            if (arrTemp.length == 1) {
                exit = false;
            }
        } while (exit);

        return result;
    }

    public int getMin(int[][] arr) {
        int min = arr[0][0];
        System.out.println();
        for (int[] arr2 : arr) {
            for (int vol : arr2) {
                if (vol != 0 && min > vol) {
                    min = vol;

                }
            }
        }
        return min;
    }

    public int getMax(int[][] arr) {
        int max = arr[0][0];
        for (int[] arr2 : arr) {
            for (int vol : arr2) {
                if (max < vol) {
                    max = vol;
                }
            }
        }
        return max;
    }

}

