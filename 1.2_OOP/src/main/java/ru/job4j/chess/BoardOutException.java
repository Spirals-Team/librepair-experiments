package ru.job4j.chess;
/**
 *Класс BoardOutException.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class BoardOutException extends RuntimeException {
    /**
     * Конструктор.
     * @param msg сообщение об ошибке.
     */
    public BoardOutException(String msg) {
        super(msg);
    }
}