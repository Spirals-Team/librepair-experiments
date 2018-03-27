package ru.job4j.litle.worldofwarcraft.solgers.mage;

import ru.job4j.litle.worldofwarcraft.solgers.Attack;
import ru.job4j.litle.worldofwarcraft.solgers.Soldier;

import java.util.List;

/**
 *Класс Некроманта .
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.09.17;
 * @version $Id$
 * @since 0.1
 */
public class MageOfUndead extends Soldier implements Attack {
    /**
     * Виды оружия.
     */
    private final Weapon[] weapons = {new Weapon("урон магией", 5.0),
            new Weapon("насылает недуг ", 0.5)
    };
    /**
     * Конструктор.
     */
    public MageOfUndead() {
        super("Орда Некромант!!!");
    }

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
            log = String.format("%s наносит %s на %.2f XP противнику %s", this.getName(), weapon.getName(),
                    damage, soldier.getName());
        } else {
            soldier = selectTarget(teamForAttack);
            soldier.setCurse();
            log = String.format("%s  %s на %s в размере %.2f %%.", this.getName(), weapon.getName(),
                    soldier.getName(), (weapon.getDamage() * 100 - 100));
        }
        return log;
    }
}
