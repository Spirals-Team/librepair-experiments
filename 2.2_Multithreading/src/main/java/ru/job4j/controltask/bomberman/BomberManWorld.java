package ru.job4j.controltask.bomberman;

import ru.job4j.controltask.bomberman.board.Board;
import ru.job4j.controltask.bomberman.entities.BomberMan;
import ru.job4j.controltask.bomberman.entities.Monster;
import java.util.ArrayList;
import java.util.List;

/**
 * Class BomberManWorld.
 */
public class BomberManWorld {
    /**
     * @param board our game board
     */
    private final Board board;
    /**
     * @param allEntity list of threads launched
     */
    private final List<Thread> allEntity = new ArrayList<>();

    /**
     * Constructor.
     * @param x count of rows
     * @param y count of column
     */
    public BomberManWorld(int x, int y) {
        this.board = new Board(x, y);
        int monsterCount = (board.getMaxX() + board.getMaxY()) / 4;
        Thread bomberman = new Thread(new BomberMan(this.board));
        bomberman.setName("Bomberman");
        allEntity.add(bomberman);
        int count = 0;
        for (int i = 0; i < monsterCount; i++) {
            Thread monster = new Thread(new Monster(this.board, bomberman));
            monster.setName("Monster" + count++);
            allEntity.add(monster);

        }
    }

    /**
     * Method for initializing the world.
     * @throws InterruptedException exception
     */
    public void init() throws InterruptedException {
        for (Thread th : allEntity) {
            th.start();
        }
        for (Thread th : allEntity) {
            th.join();
        }
    }
}
