package ru.job4j.litle.worldofwarcraft.solgers.warrior;


import ru.job4j.litle.worldofwarcraft.solgers.Attack;
import ru.job4j.litle.worldofwarcraft.solgers.Soldier;
import java.util.List;

/**
 * Воин человек.
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.09.17;
 * @version $Id$
 * @since 0.1
 */
public class WarriorOfHumans extends Soldier implements Attack {
    /**
     * Набор вооружения.
     */
    private final Weapon[] weapons = {new Weapon("урон мечом", 18.0)};

    /**
     * Конструктор.
     */
    public WarriorOfHumans() {
        super("Альянс Человеческий Воин");
    }

    /**
     * Метод атаки вражеского солдата.
     * @param team союзная команда.
     * @param teamForAttack вражеская команда.
     * @return строка для логера.
     */
    @Override
    public String attack(List<Soldier> team, List<Soldier> teamForAttack) {
        return mieleOrArcherAttak(teamForAttack, weapons);
    }
}

