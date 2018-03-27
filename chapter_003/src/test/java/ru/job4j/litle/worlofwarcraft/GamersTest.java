//package ru.job4j.litle.worlofwarcraft;
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//import org.junit.Test;
//import ru.job4j.litle.worldofwarcraft.Game;
//import ru.job4j.litle.worldofwarcraft.random.RandomForTests;
//import ru.job4j.litle.worldofwarcraft.solgers.Soldier;
//import ru.job4j.litle.worldofwarcraft.solgers.mage.MageOfElvis;
//import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfOrc;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Test.
// *
// * @author Hincu Andrei (andreih1981@gmail.com) by 22.09.17;
// * @version $Id$
// * @since 0.1
// */
//public class GamersTest {
//    /**
//     * Тест.
//     */
//    @Test
//    public void whenSoldierAttackRandomSoldier() {
//        Game game = new Game(new RandomForTests(new ArrayList<>(), new ArrayList<>()));
//        MageOfElvis mageOfElvis = new MageOfElvis();
//        mageOfElvis.setGame(game);
//        mageOfElvis.getClass().getDeclaredMethods();
//        List<Soldier> list = new ArrayList<>();
//        list.add(mageOfElvis);
//        WarriorOfOrc warrior = new WarriorOfOrc();
//        warrior.setGame(game);
//        warrior.meleeAttack(list);
//        Soldier soldier = list.get(0);
//        double re = soldier.getHp();
//       assertThat(re, is(80.0));
//    }
//}
