//package ru.job4j.litle.worlofwarcraft;
//import org.junit.Test;
//import ru.job4j.litle.worldofwarcraft.solgers.Soldier;
//import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfHumans;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
///**
// * Attacks Test.
// * @author Hincu Andrei (andreih1981@gmail.com)on 23.09.2017.
// * @version $Id$.
// * @since 0.1.
// */
//public class AttacksTest {
//
//    /**
//     *Тест урона противника.
//     */
//    @Test
//    public void whenTeamHasOnePleerForAttackAndHisHpBefoAttackDiminish() {
//        List<Soldier> list = new ArrayList<>();
//        list.add(new WarriorOfHumans());
//        List<Soldier> result = Attacks.attack(list, 18.0, "Warrior", "Attack");
//        Soldier warrior = result.get(0);
//        double hp = warrior.getHp();
//        assertThat(hp, is(82.0));
//
//    }
//
//    /**
//     * Тест если солдат умер то его удаляют из листа команды.
//     */
//    @Test
//    public void whenSoldierIsDieThenListisEmpti() {
//        List<Soldier> list = new ArrayList<>();
//        list.add(new WarriorOfHumans());
//        List<Soldier> result = Attacks.attack(list, 108.0, "Warrior", "Attack");
//        int size = result.size();
//        assertThat(size, is(0));
//    }
//
//    /**
//     * Тест получения бафа солдатом.
//     */
//    @Test
//    public void whenBaffIsImposedThenSoldierHasBaff() {
//        List<Soldier> list = new ArrayList<>();
//        list.add(new WarriorOfHumans());
//        List<Soldier> result = Attacks.bufSoldiers(list, 0.5, "Warrior", "Attack", 3);
//        Soldier soldier = result.get(0);
//        boolean re = soldier.isPremium();
//        boolean ex = true;
//        assertThat(re, is(ex));
//    }
//}
