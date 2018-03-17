package ru.job4j.litle.worldofwarcraft.solgers.archers;

import ru.job4j.litle.worldofwarcraft.solgers.Soldier;
import java.util.List;

/**
 *Орк лучник.
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.09.17;
 * @version $Id$
 * @since 0.1
 */
public class ArcherOrc extends Soldier {
    /**
     * Набор вооружения.
     */
    private final Weapon[] weapons = {new Weapon("урон от выстрела из лука", 3.0), new Weapon("урон клинком", 2.0)};
    /**
     * Конструктор.
     */
    public ArcherOrc() {
        super("Орда Орк Лучник");
    }

    /**
     * Метод атаки вражеского солдата.
     * @param team союзная команда.
     * @param teamForAttack вражеская команда.
     *@return строка для логера.
     */
    @Override
    public String attack(List<Soldier> team, List<Soldier> teamForAttack) {
        return mieleOrArcherAttak(teamForAttack, weapons);
    }
}
