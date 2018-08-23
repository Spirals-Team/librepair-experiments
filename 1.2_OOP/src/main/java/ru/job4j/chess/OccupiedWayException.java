package ru.job4j.chess;
/**
 *Класс OccupiedWayException.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class OccupiedWayException extends RuntimeException {
    /**
     * Конструктор.
     * @param msg сообщение об ошибке.
     */
    public OccupiedWayException(String msg) {
        super(msg);
    }
}
