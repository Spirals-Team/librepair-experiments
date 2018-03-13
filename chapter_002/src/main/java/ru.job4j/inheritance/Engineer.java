package ru.job4j.inheritance;
/**
 * Engineer.
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 17.10.2017
 */
public class Engineer extends Profession {

    /**
     * build.
     * @return Newhouse.
     */
    public House build() {
        House newHouse = new House();
        newHouse.setEngineer(this);
        newHouse.setGoodCondition(true);
        // System.out.println("Инженер:" + this.getName() + " построил дом");
        return newHouse;
    }

    /**
     * create auto.
     * @return created auto.
     */
    public Vechicle create() {
        Vechicle newVechicle = new Vechicle();
        newVechicle.setEngineer(this);
        newVechicle.setGoodCondition(true);
        // System.out.println("Инженер:" + this.getName() + " создал автомобиль");
        return newVechicle;
    }

    /**
     * repair house.
     * @param house house to be repaired.
     * @return message that house have been repaired.
     */
    public String repairHouse(House house) {
        house.setGoodCondition(true);
        return "Инженер: " + this.getHuman().getName() + " отремонтировал дом";
    }

    /**
     * repair auto.
     * @param vechicle auto to be repaired
     * @return message that house have been repaired
     */
    public String repairVechicle(Vechicle vechicle) {
        vechicle.setGoodCondition(true);
        return "Инженер: " + this.getHuman().getName() + " отремонтировал автомобиль";
    }
}