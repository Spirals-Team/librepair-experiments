//package ru.job4j.litle.worlofwarcraft;
//
//import org.junit.Test;
//import ru.job4j.litle.worldofwarcraft.solgers.warrior.WarriorOfHumans;
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
///**
// * Solidertest.
// * @author Hincu Andrei (andreih1981@gmail.com)on 23.09.2017.
// * @version $Id$.
// * @since 0.1.
// */
//public class SoldierTest {
//    /**
//     * Тест снижения дамага под проклятьем от некроманта.
//     */
//    @Test
//    public void whenPowerOfDamageIsCruse()  {
//        WarriorOfHumans warriorOfHumans = new WarriorOfHumans();
//        warriorOfHumans.setCurse(true);
//        double result = warriorOfHumans.poverOfDamage(18);
//        assertThat(result, is((double) 18 / 2));
//    }
//
//    /**
//     * Тест бафа союзного мага.
//     */
//    @Test
//    public void whenPowerOfDamageSetBaffed()  {
//        WarriorOfHumans warriorOfHumans = new WarriorOfHumans();
//        warriorOfHumans.moveToPremium();
//        warriorOfHumans.setGottenBaff(0.5);
//        double result = warriorOfHumans.poverOfDamage(18);
//        assertThat(result, is(27.0));
//    }
//
//    /**
//     * Тест когда союзный маг дал баф, но некромант проклял.
//     */
//    @Test
//    public void whenPowerOfDamageSetBaffedAndCrused() {
//        WarriorOfHumans warriorOfHumans = new WarriorOfHumans();
//        warriorOfHumans.moveToPremium();
//        warriorOfHumans.setGottenBaff(0.5);
//        warriorOfHumans.setCurse(true);
//        double result = warriorOfHumans.poverOfDamage(18);
//        assertThat(result, is(13.5));
//    }
//}
