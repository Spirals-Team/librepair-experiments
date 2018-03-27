package ru.job4j.litle.worldofwarcraft.solgers.mage;

import ru.job4j.litle.worldofwarcraft.solgers.Attack;
import ru.job4j.litle.worldofwarcraft.solgers.Soldier;
import java.util.List;

/**
 * Маг эльфов.
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.09.17;
 * @version $Id$
 * @since 0.1
 */
public class MageOfElvis extends Soldier implements Attack {
    /**
     * Набор вооружения.
     */
    private final Weapon[] weapons = {new Weapon("урон магией", 10.0),
            new Weapon("улучшение характеристик", 0.5)};

    /**
     * Конструктор.
     */
    public MageOfElvis() {
        super("Альянс Эльфийский маг!!!");
    }

    /**
     * Метод атаки вражеского  солдата или бафа союзника.
     * @param team союзная команда.
     * @param teamForAttack вражеская команда.
     * @return строка для логера.
     */
    @Override
    public String attack(List<Soldier> team, List<Soldier> teamForAttack) {
        Weapon weapon = selectWeapon(weapons);
        Soldier soldier;
        double damage;
        String log = "";
        if (weapon.equals(weapons[0])) {
            damage = poverOfDamage(weapon.getDamage());
            soldier = selectTarget(teamForAttack);
            soldier.damage(damage);
        log = String.format("%s наносит %s %.2f XP противнику %s.", this.getName(), weapon.getName(),
                damage, soldier.getName());
        } else {
            soldier = selectTarget(team);
            soldier.moveToPremium();
            log = String.format("%s накладывает %s на %s в размере %.2f %%. ", this.getName(), weapon.getName(),
                    soldier.getName(), (soldier.getPremium() * 100 - 100));
        }
        return log;
    }
}
