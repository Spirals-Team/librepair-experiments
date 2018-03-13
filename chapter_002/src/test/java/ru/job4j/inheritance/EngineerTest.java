package ru.job4j.inheritance;

import org.junit.Test;


import static org.junit.Assert.assertEquals;


/**
 * test Engineer.
 */
public class EngineerTest {
    /**
     * test build.
     */
    @Test
    public void whenEngineerBuildHouse() {

        Human humanEngineer = new Human();
        humanEngineer.setName("Иван");
        Engineer engineer = new Engineer();
        engineer.setHuman(humanEngineer);

        House result = engineer.build();

        House expectedHouse = new House();
        expectedHouse.setEngineer(engineer);
        expectedHouse.setGoodCondition(true);

        assertEquals(expectedHouse, result);
    }

    /**
     * test create.
     */
    @Test
    public void whenEngineerCreateVechicle() {

        Human humanEngineer = new Human();
        humanEngineer.setName("Иван");
        Engineer engineer = new Engineer();
        engineer.setHuman(humanEngineer);

        Vechicle result = engineer.create();

        Vechicle expectedVechicle = new Vechicle();
        expectedVechicle.setEngineer(engineer);
        expectedVechicle.setGoodCondition(true);

        assertEquals(expectedVechicle, result);
    }

    /**
     * test repair house.
     */
    @Test
    public void whenEngineerrepairHouse() {

        House house = new House();
        house.setGoodCondition(false);

        Human humanEngineer = new Human();
        humanEngineer.setName("Иван");
        Engineer engineer = new Engineer();
        engineer.setHuman(humanEngineer);

        house.setEngineer(engineer);

        String result = engineer.repairHouse(house);

        String expectedResult = "Инженер: Иван отремонтировал дом";

        assertEquals(expectedResult, result);
    }

    /**
     * test repair auto.
     */
    @Test
    public void whenEngineerrepairVechicle() {

        Vechicle vechicle = new Vechicle();
        vechicle.setGoodCondition(false);

        Human humanEngineer = new Human();
        humanEngineer.setName("Иван");
        Engineer engineer = new Engineer();
        engineer.setHuman(humanEngineer);

        vechicle.setEngineer(engineer);

        String result = engineer.repairVechicle(vechicle);

        String expectedResult = "Инженер: Иван отремонтировал автомобиль";

        assertEquals(expectedResult, result);
    }
}
