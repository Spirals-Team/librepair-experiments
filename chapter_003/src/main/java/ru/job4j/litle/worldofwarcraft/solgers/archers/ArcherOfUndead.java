package ru.job4j.litle.worldofwarcraft.solgers.archers;

import ru.job4j.litle.worldofwarcraft.solgers.Soldier;
import java.util.List;

/**
 * Нежить лучник.
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.09.17;
 * @version $Id$
 * @since 0.1
 */
public class ArcherOfUndead extends Soldier {
    /**
     * Набор вооружения.
     */
    private final Weapon[] weapons = {new Weapon("урон от выстрела из лука", 4.0), new Weapon("урон мечом", 2.0)};

    /**
     * Конструктор.
     */
    public ArcherOfUndead() {
        super("Орда Охотник");
    }

    /**
     *  Метод атаки вражеского солдата.
     * @param team  команда союзников.
     * @param teamForAttack команда противников.
     * @return строка для логера.
     */
    @Override
    public String attack(List<Soldier> team, List<Soldier> teamForAttack) {
        return mieleOrArcherAttak(teamForAttack, weapons);
    }
}
