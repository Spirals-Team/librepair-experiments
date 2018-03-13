package ru.job4j.inheritance;
/**
 * doctor.
 */
public class Doctor extends Profession {
    /**
     * diploma.
     */
    private String diploma;

    /**
     *
     * @param human return string result.
     * @return string result.
     */
    public String heal(Human human) {

        human.setGoodHealth(true);

        return "Доктор " + this.getHuman().getName() + " лечит " + human.getName();
    }
}
