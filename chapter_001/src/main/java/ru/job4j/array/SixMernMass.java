package ru.job4j.array;
/**
 * Заполнение шестимерного массива
 */

import java.util.Scanner;

public class SixMernMass {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите размерность массива: ");
        int sizeArr = in.nextInt();
        int[][][][][][] sixMass = new int[sizeArr][sizeArr][sizeArr][sizeArr][sizeArr][sizeArr];
        int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0;
        int res = 1;
        do {
            if (b < sizeArr && c < sizeArr && d < sizeArr && e < sizeArr && f < sizeArr) {
                System.out.print("a = " + a + " b = " + b + " c= " + c + " d = " + d + " e = " + e + " f= " + f + "; элемент = ");
                sixMass[a][b][c][d][e][f] = res++;
                System.out.println(sixMass[a][b][c][d][e][f] + " ");
                f++;
            } else if (e < sizeArr) {
                e++;
                f = 0;
                res = 1;
            } else if (d < sizeArr) {
                d++;
                e = 0;
                f = 0;
                res = 1;
            } else if (c < sizeArr) {
                c++;
                d = 0;
                e = 0;
                f = 0;
                res = 1;
            } else if (b < sizeArr) {
                b++;
                c = 0;
                d = 0;
                e = 0;
                f = 0;
                res = 1;
            } else {
                a++;
                b = 0;
                c = 0;
                d = 0;
                e = 0;
                f = 0;
                res = 1;
            }
        } while (a < sizeArr);
        System.out.println(sixMass[1][1][1][1][1][1]);
    }
}
