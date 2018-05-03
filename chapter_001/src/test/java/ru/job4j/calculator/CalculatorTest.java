package ru.job4j.calculator;
/**@autor Alexander Kaleganov
 * @version 1.001
 * @since 26.01.2018 22:25
 */
import org.junit.Test;
import static org.hamcrest.core.Is.is; // я так и не понял на кой хрен вот этот класс импортируется но без него работать не будет
import static org.junit.Assert.assertThat; //а вот без этого класса точно не будет сравниваться предсказуемое число и полученный результат теста

public class CalculatorTest {
    /**создадим объект calc класса Calculator
     * далее новые объекты создавать не будем
     * мы просто будем проверять разные методы этого объекта    *
     */
    Calculator calc = new Calculator();


    @Test // так бозначают тестовые методы, эти методы должны быть public void
    /** Вот тут запустим тестирование метода сложения
     */
    public void whenAddOnePlusOneThenTwo() {
        calc.add(1D, 1D);
        double result = calc.getResult();
        double vanga = 2d;
        assertThat(result, is(vanga));
    }
    /**тестируем метод вычитание
     */
    public void whenSubstractDvaMinusOneThenOne() {
        calc.substract(2d, 1d);
        double result = calc.getResult();
        double vanga = 1d;
        assertThat(result, is(vanga));
    }
    /**тестируем метод деление
     */
    public void whenDiv4tiriDelitDvaThenDva() {
        calc.div(4d, 2d);
        double result = calc.getResult();
        double vanga = 2d;
        assertThat(result, is(vanga));
    }
    /**тестируем метод умножение
     */
    public void whenmultipleDvaUmnozitDvaThen4tiri() {
        calc.multiple(2d, 2d);
        double result = calc.getResult();
        double vanga = 4d;
        assertThat(result, is(vanga));
    }
}
