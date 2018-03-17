package ru.job4j.litle.worldofwarcraft.random;

import ru.job4j.litle.worldofwarcraft.solgers.Soldier;
import ru.job4j.litle.worldofwarcraft.solgers.archers.ArcherOfElvis;
import ru.job4j.litle.worldofwarcraft.solgers.archers.ArcherOfHumans;
import ru.job4j.litle.worldofwarcraft.solgers.archers.ArcherOfUndead;
import ru.job4j.litle.worldofwarcraft.solgers.archers.ArcherOrc;
import ru.job4j.litle.worldofwarcraft.solgers.mage.MageOfElvis;
import ru.job4j.litle.worldofwarcraft.solgers.mage.MageOfHumans;
import ru.job4j.litle.worldofwarcraft.solgers.mage.MageOfOrc;
import ru.job4j.litle.worldofwarcraft.solgers.mage.MageOfUndead;
import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfElvis;
import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfHumans;
import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfOrc;
import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfUndead;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *Класс формирования команд.
 * @author Hincu Andrei (andreih1981@gmail.com) by 21.09.17;
 * @version $Id$
 * @since 0.1
 */
public class RandomAndTeamsSettings implements RandomInterface {

    /**
     * Генератор случайных чисел в диапазоне.
     * @param from от.
     * @param to до.
     * @return случайное число.
     */
    public static int getRandomInt(int from, int to) {
        return (from + new Random().nextInt(to));
    }

    /**
     * Формирование команды альянса в случайн ом порядке.
     * @return готовая к бою команда.
     */
    public  List<Soldier> getAlians() {
        List<Soldier> team = new ArrayList<>();
        int randomInt = getRandomInt(0, 2);
        if (randomInt == 0) {
            team.add(new MageOfElvis());
        } else {
            team.add(new MageOfHumans());
        }
        for (int i = 0; i < 3; i++) {
            randomInt = getRandomInt(0, 2);
            if (randomInt == 0) {
                team.add(new ArcherOfElvis());
            } else {
                team.add(new ArcherOfHumans());
            }
        }
        for (int i = 0; i < 4; i++) {
            randomInt = getRandomInt(0, 2);
            if (randomInt == 0) {
                team.add(new WarriorOfElvis());
            } else {
                team.add(new WarriorOfHumans());
            }
        }
        return team;
    }
    /**
     * Формирование команды орды в случайн ом порядке.
     * @return готовая к бою команда.
     */
    public  List<Soldier> getOrda() {
        List<Soldier> team = new ArrayList<>();
        int randomInt = getRandomInt(0, 2);
        if (randomInt == 0) {
            team.add(new MageOfUndead());
        } else {
            team.add(new MageOfOrc());
        }
        for (int i = 0; i < 3; i++) {
            randomInt = getRandomInt(0, 2);
            if (randomInt == 0) {
                team.add(new ArcherOfUndead());
            } else {
                team.add(new ArcherOrc());
            }
        }
        for (int i = 0; i < 4; i++) {
            randomInt = getRandomInt(0, 2);
            if (randomInt == 0) {
                team.add(new WarriorOfUndead());
            } else {
                team.add(new WarriorOfOrc());
            }
        }
        return team;
    }

    /**
     * Запись лога в файл.
     * @param data лог.
     */
    public static void writeLog(String data) {
        File file = new File("/Users/Public/LogGameOfWarOfWarcraft.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
