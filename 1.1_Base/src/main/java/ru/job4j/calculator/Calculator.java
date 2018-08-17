package ru.job4j.calculator;

/**
 *Калькулятор с выполнением базовых операций.
 *@author ifedorenko
 *@since 13.08.2017
 *@version 1
 */
public class Calculator {
    /**
	*@param хранит результат операции
	*/
	private double result;
	/**
	*Сложение.
	*@param first содержит первое число
	*@param second содержит второе число
	*/
    public void add(double first, double second) {
        this.result = first + second;
    }
	/**
	*Вычитание.
	*@param first уменьшаемое число
	*@param second вычитаемое число
	*/
	public void subtract(double first, double second) {
        this.result = first - second;
	}
	/**
	*Деление.
	*@param first делимое число
	*@param second делитель
	*/
	public void div(double first, double second) {
        this.result = first / second;
    }
	/**
	*Умножение.
	*@param first множимое число
	*@param second множитель
	*/
	public void multiple(double first, double second) {
        this.result = first * second;
    }
    /**
	*@return Результат.
	*/
	public double getResult() {
        return this.result;
    }
}