package ru.job4j.automat;

/**
 * Class Automat.
 */
public class Automat {
    /**
     * Массив манет. Вносимых либо выдаваемых.
     */
    private int[] userCoins = new int[10];
    /**
     * Позиция для массива.
     */
    private int position = 0;
    /**
     * Количество монет номиналом 10.
     */
    private int ten = 10;
    /**
     * Количество монет номиналом 5.
     */
    private int five = 10;
    /**
     * Количество монет номиналом 2.
     */
    private int two = 10;
    /**
     * Количество монет номиналом 1.
     */
    private int one = 10;
    /**
     * Сумма денег внесенных пользователем.
     */
    private int summaInput = 0;

    /**
     * Метод внесения денег в автомат.
     * @param money принимаем монету от пользователя.
     */
    public void makeMoney(int money) {
        this.userCoins[position++] = money;
    }

    /**
     * Геттер вернет сумму.
     * @return вернем сумму внесенную пользователем.
     */
    public int getSummaInput() {
        return summaInput;
    }

    /**
     * Метод разложит монеты по номеналу.
     */
    public void insertedMoney() {
        for (int coin : this.userCoins) {
            this.summaInput = this.summaInput + coin;
            if (coin == 10) {
                this.ten++;
            } else if (coin == 5) {
                this.five++;
            } else if (coin == 2) {
                this.two++;
            } else if (coin == 1) {
                this.one++;
            }
        }
        resetArray();
    }

    /**
     * Метод инофрмации о количестве монет разных номеналов в автомате.
     * @return вернем сообщение с количеством монет.
     */
    public String info() {
        return String.format("Десять: %s. Пять: %s. Два: %s. Один: %s", ten, five, two, one);
    }

    /**
     * Метод покупки печенью за 18 рублей.
     */
    public void toBuyCookies() {
        if (summaInput >= 18) {
            summaInput = summaInput - 18;
            System.out.println("Вы купили печенье. У вас осталось: " + summaInput);
        } else {
            System.out.println("У вас не хватает манет: " + summaInput);
        }
    }

    /**
     * Метод выдачи сдачи.
     */
    public void toGiveACoin() {
        resetArray();
        while (this.summaInput != 0) {
            int[] arrey = new int[]{10, 5, 2, 1};
            for (int m : arrey) {
                if ((this.summaInput / m) >= 1) {
                    userCoins[position++] = m;
                    this.summaInput = this.summaInput - m;
                }
            }
        }
        for (int coin : userCoins) {
            if (coin != 0) {
                System.out.println("Ваша сдача: " + coin);
            }
        }
        resetArray();
    }

    /**
     * Метод обнуляет массив.
     */
    public void resetArray() {
        for (int i = 0; i < userCoins.length; i++) {
            userCoins[i] = 0;
        }
        position = 0;
    }

}
