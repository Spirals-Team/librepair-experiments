package ru.job4j.prof;

/**
 * это класс инженера он умеет  чиниь
 */
import ru.job4j.prof.dopclass.*;


public class Engineer extends Profession {
    private Direction direction;

    public Direction getDirection() {
        return this.direction;
    }
    public Engineer(String name, Sex sex, int experience, Education education, Direction direction) {
        super(name, sex, experience, education);
        this.direction = direction;
    }

    public void mends(Auto auto) {
        auto.setRabsost(Rabsost.РАБОЧЕЕ);
    }
    public void mends(Pc pc) {
        pc.setRabsost(Rabsost.РАБОЧЕЕ);
    }
    public void mends(Lift lift) {
        lift.setRabsost(Rabsost.РАБОЧЕЕ);
    }
}
