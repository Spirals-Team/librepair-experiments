//package ru.job4j.litle.worlofwarcraft;
//
//import org.junit.Test;
//import ru.job4j.litle.worldofwarcraft.Game;
//import ru.job4j.litle.worldofwarcraft.random.RandomForTests;
//import ru.job4j.litle.worldofwarcraft.solgers.Soldier;
//import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfElvis;
//import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfHumans;
//import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfOrc;
//import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfUndead;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Тест.
// * @author Hincu Andrei (andreih1981@gmail.com) by 23.09.17;
// * @version $Id$
// * @since 0.1
// */
//public class BattleTest {
//    /**
//     * Тест.
//     */
//    @Test
//    public void whenBattleTwoWarriors() {
//        WarriorOfOrc warriorOfOrc = new WarriorOfOrc();
//        WarriorOfUndead warriorOfUndead = new WarriorOfUndead();
//        WarriorOfHumans warriorOfHumans = new WarriorOfHumans();
//        WarriorOfElvis warriorOfElvis = new WarriorOfElvis();
//        List<Soldier> orda = new ArrayList<>();
//        List<Soldier> alians = new ArrayList<>();
//        orda.add(warriorOfOrc);
//        orda.add(warriorOfUndead);
//        alians.add(warriorOfElvis);
//        alians.add(warriorOfHumans);
//        RandomForTests randomForTests = new RandomForTests(alians, orda);
//        Game game = new Game(randomForTests);
//        game.battle(orda, alians);
//        System.out.println(Game.builder);
//        int sizeOrda = game.getTeamOfOrda().size();
//        assertThat(sizeOrda, is(1));
//    }
//}
