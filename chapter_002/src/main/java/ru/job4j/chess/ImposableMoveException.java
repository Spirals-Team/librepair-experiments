package ru.job4j.chess;

public class ImposableMoveException extends RuntimeException {
    public ImposableMoveException(String ime) {
        super(ime);
    }
}
