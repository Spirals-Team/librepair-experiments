package ru.job4j.game;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 *
 * Интерфейс движения.
 * В методы должна передаваться текущая позиция и возвращаться новая позиция.
 */
public interface Move {
    Position moveLeft(Position currentPosition);
    Position moveRight(Position currentPosition);
    Position moveUp(Position currentPosition);
    Position moveDown(Position currentPosition);
}
