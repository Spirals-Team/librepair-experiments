package ru.job4j.litle.worldofwarcraft.solgers.mage;

import ru.job4j.litle.worldofwarcraft.solgers.Attack;
import ru.job4j.litle.worldofwarcraft.solgers.Soldier;

import java.util.List;

/**
 *Класс шамана.
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.09.17;
 * @version $Id$
 * @since 0.1
 */
public class MageOfOrc extends Soldier implements Attack {
    /**
     * Набор вооружения.
     */
    private final Weapon[] weapons = {new Weapon("снимает баф", 0.5),
            new Weapon("улучшение характеристик", 0.5)
    };

    /**
     * Конструктор.
     */
    public MageOfOrc() {
        super("Орда Шаман!!!!");
    }

    /**
     *Снятие бафа с противника или баф союзника.
     * @param team команда союзников.
     * @param teamForAttack команда противников.
     * @return строка для лога.
     */

    @Override
    public String attack(List<Soldier> team, List<Soldier> teamForAttack) {
        Weapon weapon = selectWeapon(weapons);
        Soldier soldier = null;
        String log = "";
        if (weapon.equals(weapons[1])) {
            soldier = selectTarget(team);
            soldier.moveToPremium();
            log = String.format("%s накладывает %s на %s в размере %.2f %%. ", this.getName(), weapon.getName(),
                    soldier.getName(), (soldier.getPremium() * 100 - 100));
        } else {
            for (Soldier s : teamForAttack) {
                if (s.getPremium() > 1) {
                    soldier = s;
                    break;
                }
            }
            if (soldier != null) {
                soldier.moveFromPremium();
                log = String.format("%s снимает баф с %s.", this.getName(), soldier.getName());
            } else {
                log = String.format("%s не нашел противника.", this.getName());
            }
        }
        return log;
    }
}
