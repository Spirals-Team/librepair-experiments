package ru.job4j.litle.worldofwarcraft.solgers;

import ru.job4j.litle.worldofwarcraft.random.RandomAndTeamsSettings;
import java.util.List;

/**
 *Воин.
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.09.17;
 * @version $Id$
 * @since 0.1
 */
public abstract class Soldier implements Attack {

    /**
     * хит поинт.
     */
    private double hp = 100;
    /**
     * Имя.
     */
    private  String name;

    /**
     * Установить проклятье.
     */
    public void setCurse() {
        this.curse = this.curse + 1;
    }

    /**
     * Снять проклятье.
     */
    public  void moveFromCruse() {
        this.curse = 1;
    }
    /**
     *  премиум и проклятье.
     */
    private double premium = 1.0, curse = 1.0;

    /**
     * Премиум воин.
     * @return премиум.
     */
    public double getPremium() {
        return premium;
    }

    /**
     * Конструктор.
     * @param name имя.
     */
    public Soldier(String name) {
        this.name = name;

    }
    /**
     * Геттер.
     * @return имя.
     */
    public String getName() {
        return name;
    }

    /**
     * геттер Хп.
     * @return Хп.
     */
    public double getHp() {
        return hp;
    }

    /**
     * перевести в премиум.
     */
    public void moveToPremium() {
        this.premium  = 1.5;
    }

    /**
     * Убрать с премиума.
     */
    public void moveFromPremium() {
        this.premium = 1.0;
    }

    /**
     * Полученный урон.
     * @param value урон.
     */
    public void damage(double value) {
        this.hp = this.hp - value;
    }

    /**
     * То стринг.
     * @return Стринг.
     */
    @Override
    public String toString() {
        return "Soldier{"
                + "name='"
                + name
                + '\''
                + '}';
    }

    /**
     * Метод выбора случайного оружия.
     * @param weapons набор оружия.
     * @return выбранное оружее.
     */
    public Weapon selectWeapon(Weapon[] weapons) {
        int index = RandomAndTeamsSettings.getRandomInt(0, weapons.length);
        return weapons[index];
    }
    /**
     * Уровень урона или бафа.
     * @param damage урон.
     * @return урон в зависимости от бафов или дебафов.
     */
    public double poverOfDamage(double damage) {
        damage = (damage * this.premium) / this.curse;
        this.moveFromCruse();
        return damage;
    }

    /**
     * Атака противника.
     * @param teamForAttack команда противника.
     * @param weapons виды оружия.
     * @return строка для лога.
     */
    public String mieleOrArcherAttak(List<Soldier> teamForAttack, Weapon[]weapons) {
        Weapon weapon = selectWeapon(weapons);
        double damage = poverOfDamage(weapon.getDamage());
        Soldier soldier = selectTarget(teamForAttack);
        soldier.damage(damage);
        return String.format("%s наносит %s %.2f XP противнику %s.", this.getName(), weapon.getName(),
                damage, soldier.getName());
    }
    /**
     * Equals.
     * @param o o.
     * @return yes or no.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Soldier soldier = (Soldier) o;

        if (Double.compare(soldier.hp, hp) != 0) {
            return false;
        }
        if (Double.compare(soldier.premium, premium) != 0) {
            return false;
        }
        if (Double.compare(soldier.curse, curse) != 0) {
            return false;
        }
        return name != null ? name.equals(soldier.name) : soldier.name == null;
    }
    /**
     * heshCode.
     * @return code.
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(hp);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(premium);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(curse);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Метод для выбора цели.
     * @param soldiersForTarget команда из которой выбирают.
     * @return цель для атаки или для бафа.
     */
    public Soldier selectTarget(List<Soldier> soldiersForTarget) {
        int index = RandomAndTeamsSettings.getRandomInt(0, soldiersForTarget.size());
        return soldiersForTarget.get(index);
    }

    /**
     * Класс оружие.
     */
    public class Weapon {
        /**
         * Название урона.
         */
        private String name;
        /**
         * Сила атаки.
         */
        private double damage;

        /**
         * Конструктор.
         * @param name название.
         * @param damage урон.
         */
        public Weapon(String name, double damage) {
            this.name = name;
            this.damage = damage;
        }

        /**
         * геттер.
         * @return название атаки.
         */
        public String getName() {
            return name;
        }

        /**
         * геттер.
         * @return урон.
         */

        public double getDamage() {
            return damage;
        }

        /**
         * еквилс.
         * @param o о.
         * @return true or false.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Weapon weapon = (Weapon) o;

            if (Double.compare(weapon.damage, damage) != 0) {
                return false;
            }
            return name != null ? name.equals(weapon.name) : weapon.name == null;
        }

        /**
         * heshcode.
         * @return code.
         */
        @Override
        public int hashCode() {
            int result;
            long temp;
            result = name != null ? name.hashCode() : 0;
            temp = Double.doubleToLongBits(damage);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }
}
