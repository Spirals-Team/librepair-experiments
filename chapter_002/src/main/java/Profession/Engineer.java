package profession;

/**
 * Метод buildHouse возвращает строку "Инженер Иванов строит дом высотой 20 этажей",
 * где "Иванов" это поле "name" из класса Profession, а 20 - аргумент класса House.
 *
 * @author Alexandar Vysotskiy
 * @version 1.0
 */

public class Engineer extends Profession {
    public String buildHouse() {
        Profession engineer = new Profession();
        House house = new House();
        engineer.setName("Иванов");
        engineer.setProfession("Инженер");
        house.numberFloors = 20;
        return engineer.getProfession() + " " + engineer.getName() + " строит дом высотой " + house.numberFloors + " этажей";
    }
}
