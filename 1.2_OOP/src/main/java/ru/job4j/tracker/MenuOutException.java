package ru.job4j.tracker;
/**
 *Класс MenuOutException.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class MenuOutException extends RuntimeException {
    /**
     * Конструктор.
     * @param msg сообщение об ошибке.
     */
    public MenuOutException(String msg) {
	    super(msg);
    }
}