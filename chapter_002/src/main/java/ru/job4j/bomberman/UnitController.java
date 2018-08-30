package ru.job4j.bomberman;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Unit controller.
 * Initiate thread pool, run characters.
 */
public class UnitController {

    private ExecutorService service;
    private Hero hero;
    private List<Monster> monsters = new ArrayList<>();
    private List<Runnable> units = new ArrayList<>();

    public UnitController(final int size) {
        service = Executors.newFixedThreadPool(size);
    }

    /**
     * Add hero
     *
     * @param hero hero
     */
    public void addHero(final Hero hero) {
        this.hero = hero;
        addUnit(this.hero);
    }

    /**
     * Add list of monsters
     *
     * @param monsters monsters
     */
    public void addMonsters(final List<Monster> monsters) {
        this.monsters = monsters;
        for (Monster monster : this.monsters) {
            addUnit(monster);
        }
    }

    private void addUnit(Unit unit) {
        units.add(unit);
    }

    /**
     * Run characters
     */
    public void execute() {
        for (Runnable unit : units) {
            service.execute(unit);
        }
    }

    /**
     * Get runnable units
     *
     * @return units
     */
    public List<Runnable> getUnits() {
        return units;
    }

    /**
     * Stop all threads
     */
    public void stop() {
        service.shutdownNow();
    }
}
