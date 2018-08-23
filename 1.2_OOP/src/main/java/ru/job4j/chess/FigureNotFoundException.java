package ru.job4j.chess;
/**
 *Класс FigureNotFoundException.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class FigureNotFoundException extends RuntimeException {
    /**
     * Конструктор.
     * @param msg сообщение об ошибке.
     */
    public FigureNotFoundException(String msg) {
        super(msg);
    }
}
