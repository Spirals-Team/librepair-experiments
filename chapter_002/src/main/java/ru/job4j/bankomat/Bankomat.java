package ru.job4j.bankomat;

/**
 * банкомат дающий сдачу
 */
public class Bankomat {

    public int[] changes(int value, int price) {
        int[] zdacha = new int[]{1, 2, 5, 10};
        int[] result = new int[30];
        int temp = 0;
        int j = 0;
        if (value - price > 0) {
            for (int i = zdacha.length - 1; i >= 0; i--) {
                while (value - price - (temp + zdacha[i]) >= 0) {
                    temp += zdacha[i];
                    result[j++] = zdacha[i];
                }
            }
        } else if (value - price < 0) {
            result[0] = value;
            System.out.println("недостаточно денег, заберите вашу купюру обратно");
        }
        return result;
    }
}
