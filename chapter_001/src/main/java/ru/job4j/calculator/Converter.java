package ru.job4j.calculator;

public class Converter {
    private static final  double DOLLAR = 60;
    private static final  double EURO = 70;

    /**
     * Конвертация рубли в евро.
     * @param value рубли.
     * @return Евро.
     */
    public double rubleToEuro(double value) {
      return value / EURO;
    }
    /**
     * Конвертируем рубли в доллары.
     * @param value рубли.
     * @return Доллоры.
     */
    public double rubleToDollar(double value) {
        return value / DOLLAR;
    }
    /**
     * Конвертируем доллары в рубли.
     * @param value рубли.
     * @return Доллоры.
     */
    public double dollarToRuble(double value) {
       return DOLLAR * value;
    }
    /**
     * Конвертируем евров рубли.
     * @param value рубли.
     * @return Евро.
     */
    public double euroToRuble(double value) {
         return EURO * value;
    }
}
