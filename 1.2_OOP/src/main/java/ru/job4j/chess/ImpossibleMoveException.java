package ru.job4j.chess;
/**
 *Класс ImpossibleMoveException.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class ImpossibleMoveException extends RuntimeException {
    /**
     * Конструктор.
     * @param msg сообщение об ошибке.
     */
    public ImpossibleMoveException(String msg) {
        super(msg);
    }
}

