package ru.job4j.bomberman;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.currentThread;

/**
 * Game.
 */
public class Game {

    private final List<Monster> monsters = new ArrayList<>();
    private final Hero hero;
    private final UnitController controller;

    public Game(final int boardSize, final int numberOfMonsters) {
        Board board = new Board(boardSize);
        board.arrangeRocks(calculateNumOfRocks(board.size()));
        this.controller = new UnitController(1 + numberOfMonsters);
        this.hero = new Hero(board);
        for (int i = 0; i < numberOfMonsters; i++) {
            this.monsters.add(new Monster(board));
        }
    }

    private int calculateNumOfRocks(final int num) {
        double rockFactor = 2;
        return (int) Math.round(num * rockFactor);
    }

    private void init() {
        controller.addHero(hero);
        controller.addMonsters(monsters);
        controller.execute();
    }

    /**
     * Start game
     */
    public void start() {
        init();
        while (!currentThread().isInterrupted()) {
            hero.setDirection(Direction.getRandomDirection());
        }
    }

    /**
     * Stop game
     */
    public void stop() {
        currentThread().interrupt();
        controller.stop();
    }
}
