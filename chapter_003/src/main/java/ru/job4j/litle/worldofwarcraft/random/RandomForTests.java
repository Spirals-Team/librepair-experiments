package ru.job4j.litle.worldofwarcraft.random;

import ru.job4j.litle.worldofwarcraft.solgers.Soldier;

import java.util.ArrayList;
import java.util.List;

/**
 *Класс для создания команд для тестов .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 24.09.17;
 * @version $Id$
 * @since 0.1
 */
public class RandomForTests implements RandomInterface {
    /**
     * Команда альянса.
     */
    private List<Soldier> alians = new ArrayList<>();
    /**
     * Команда орды.
     */
    private List<Soldier> orda = new ArrayList<>();

    /**
     * Конструктор.
     * @param alians альянс.
     * @param orda орда.
     */
    public RandomForTests(List<Soldier> alians, List<Soldier> orda) {
        this.alians = alians;
        this.orda = orda;
    }

    /**
     * Возращает команду альянса.
     * @return  альянс.
     */
    @Override
    public List<Soldier> getAlians() {
        return alians;
    }

    /**
     * Возвращает команду орды.
     * @return орда.
     */
    @Override
    public List<Soldier> getOrda() {
        return orda;
    }
}
