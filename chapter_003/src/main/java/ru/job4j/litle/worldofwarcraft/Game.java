package ru.job4j.litle.worldofwarcraft;

import ru.job4j.litle.worldofwarcraft.random.RandomAndTeamsSettings;
import ru.job4j.litle.worldofwarcraft.random.RandomInterface;
import ru.job4j.litle.worldofwarcraft.solgers.Soldier;


import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс игры.
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.09.17;
 * @version $Id$
 * @since 0.1
 */
public class Game {
    /**
     * Получене команд.
     */
    private RandomInterface random;

    /**
     * Конструктор.
     * @param random случайные команды.
     */
    public Game(RandomInterface random) {
        this.random = random;
        this.alians = random.getAlians();
        this.orda = random.getOrda();

    }
    /**
     * Майн.
     * @param args нету.
     */
    public static void main(String[] args) {
        Game game = new Game(new RandomAndTeamsSettings());
        game.start();
        System.out.println(builder);
        //RandomAndTeamsSettings.writeLog(builder.toString());
    }

    /**
     * Запись происходящего.
     */
    private static StringBuilder builder = new StringBuilder();
    /**
     * Новая строка.
     */
    private  String newLine = System.getProperty("line.separator");
    /**
     * Комманда орды.
     */
    private  List<Soldier> orda;
    /**
     * Команда альянса.
     */
    private  List<Soldier> alians;
    /**
     * Переход хода.
     */
    private boolean flag = false;

    /**
     * Флаг.
     * @return flag.
     */
    private boolean isFlag() {
        return flag;
    }

    /**
     * setter.
     * @param flag flag.
     */
    private void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * Команда элитных войнов.
     */
    private List<Soldier> premium = new ArrayList<>();

    /**
     * Старт.
     */
    public void start() {
        infoAboutTeams();
        whoIsFirstStartGame();
        if (isFlag()) {
            battle(alians, orda);
        } else {
            battle(orda, alians);
        }
    }

    /**
     * Битва.
     * @param teamAttack атакующая комманда.
     * @param teamForDamage получающая команда.
     */
    public void battle(List<Soldier> teamAttack, List<Soldier> teamForDamage) {

        Soldier soldierAttack = null;
        /**
         * проверяем нет ли в премиум команде войнов из атакующей команды.
         */
        if (!premium.isEmpty()) {
            for (Soldier sol : premium) {
                if (teamAttack.contains(sol)) {
                    soldierAttack = sol;

                }
            }
        }
        /**
         * Если воин найден то он атакует иначе атакует случайный воин.
         */
        if (soldierAttack != null) {
            premium.remove(soldierAttack);
            String s = soldierAttack.attack(teamAttack, teamForDamage);
            soldierAttack.moveFromPremium();
            builder.append(s).append(newLine);
        } else {
            int index = 0;
            if (soldierAttack == null) {
                index = RandomAndTeamsSettings.getRandomInt(0, teamAttack.size());
                soldierAttack = teamAttack.get(index);
            }
            String s = soldierAttack.attack(teamAttack, teamForDamage);
            builder.append(s).append(newLine);
        }
        /**
         * Перевод солдат с бафом в элитный отряд.
         */
        for (Soldier soldier : teamAttack) {
            if (soldier.getPremium() > 1) {
                premium.add(soldier);
                builder.append(soldier.getName()).append(" переведен в элитную группу.").append(newLine);
                break;
            }
        }
        /**
         * Удаление погибших солдат.
         */
        for (Soldier soldier : teamForDamage) {
            if (soldier.getHp() < 1) {
                soldierAttack = soldier;
                teamForDamage.remove(soldierAttack);
                builder.append(soldierAttack.getName()).append(" погибает.").append(newLine);
                break;
            }
        }
        /**
         * Если в обоих командах есть войны бой продолжается.
         */
        if (!this.alians.isEmpty() && !this.orda.isEmpty()) {
            replaceFlag();
            if (isFlag()) {
                battle(this.alians, this.orda);
            } else {
                battle(this.orda, this.alians);
            }
        } else if (this.alians.isEmpty()) {
            builder.append("Отряд орды Победил!!").append(newLine).append("Выжевшие герои :").append(newLine);
            for (Soldier soldier : orda) {
                builder.append(soldier.getName()).append(" XP: ").append(soldier.getHp()).append(newLine);
            }
        } else {
            builder.append("Отряд альянса Победил!!").append(newLine).append("Выжевшие герои :").append(newLine);
            for (Soldier soldier : alians) {
                builder.append(soldier.getName()).append(" XP: ").append(soldier.getHp()).append(newLine);
            }
        }
    }

    /**
     * Кто первым начнет игру.
     */
    private void whoIsFirstStartGame() {
        builder.append("Атаку начал первым отряд ");
        int i = RandomAndTeamsSettings.getRandomInt(0, 2);
        if (i == 0) {
            builder.append("орды.").append(newLine).append("=============================").append(newLine);
            setFlag(false);
        } else {
            builder.append("альянса").append(newLine).append("=============================").append(newLine);
            setFlag(true);
        }
    }
    /**
     * Инфо о коммандах.
     */
    private void infoAboutTeams() {
        builder.append("Патрулируя границы Земноморья отряд втретил отряд орды.").append(newLine);
        builder.append("Отряды состояли из следующих войнов :").append(newLine).append("Отряд альянса :").append(newLine).append(newLine);
        for (Soldier s : this.alians) {
            builder.append(s.getName()).append(newLine);
        }
        builder.append(newLine).append("Отряд орды:").append(newLine);
        for (Soldier s : this.orda) {
            builder.append(s.getName()).append(newLine);
        }
    }

    /**
     * Чередование ходов.
     */
    private void replaceFlag() {
        if (isFlag()) {
            this.flag = false;
        } else {
            this.flag = true;
        }
    }
}


