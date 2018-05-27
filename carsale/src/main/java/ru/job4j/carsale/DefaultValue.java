package ru.job4j.carsale;

import ru.job4j.models.Brand;
import ru.job4j.models.Model;
import ru.job4j.models.User;

import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class DefaultValue {
    private final CarStorage carStorage = CarStorage.INSTANCE;

    public void addBrand() {
        if (carStorage.getList(Brand.class.getSimpleName()).size() == 0) {
            String[] brands = {"LADA", "Audi", "BMW", "Cadillac", "Chery", "Chevrolet", "Chrysler", "Citroen", "Daewoo",
                    "Dodge", "Fiat", "Ford", "Geely", "Honda", "Hyundai", "Infiniti", "Jaguar", "Jeep", "Kia", "LandRover",
                    "Lexus", "Lifan", "MINI", "Mazda", "Mercedes-Benz", "Mitsubishi", "Nissan", "Opel", "Peugeot",
                    "Porsche", "Renault", "Saab", "Skoda", "SsangYong", "Subaru", "Suzuki", "Toyota", "Volkswagen", "Volvo",
                    "GAZ", "UAZ", "Другая"};
            for (String st : brands) {
                carStorage.addObject(new Brand(st));
            }
        }
    }

    public void addModel() {
        if (carStorage.getList(Model.class.getSimpleName()).size() == 0) {
            List<Brand> list = carStorage.getList(Brand.class.getSimpleName());
            for (Brand brand : list) {
                int id = brand.getId();
                carStorage.addObject(new Model("Другая", id));
            }
            String brandName = "Audi";
            String[] models = {"A1", "A3", "A4", "A5", "A6", "A7", "A8", "Q3", "Q5", "Q7", "TT"};
            int id = carStorage.getBrand(brandName).getId();
            for (String st : models) {
                carStorage.addObject(new Model(st, id));
            }
            brandName = "BMW";
            models = new String[]{"1 Series", "2 Series", "3 Series", "4 Series", "5 Series", "6 Series", "7 Series",
                    "X1 Series", "X2 Series", "X3 Series", "X4 Series", "X5 Series", "X6 Series"};
            id = carStorage.getBrand(brandName).getId();
            for (String st : models) {
                carStorage.addObject(new Model(st, id));
            }
            brandName = "Mercedes-Benz";
            models = new String[]{"A Klasse", "B Klasse", "C Klasse", "E Klasse", "S Klasse", "CLA Klasse", "CLE Klasse",
                    "G Klasse", "GLA Klasse", "GLC Klasse", "GLE Klasse", "GLS Klasse"};
            id = carStorage.getBrand(brandName).getId();
            for (String st : models) {
                carStorage.addObject(new Model(st, id));
            }
        }
    }

    public void addRoot() {
        List<User> list = carStorage.getList(User.class.getSimpleName());
        if (list.size() == 0) {
            carStorage.addObject(new User("root", "root"));
        } else {
            for (User user : list) {
                if (user.getLogin().equals("root")) {
                    return;
                }
            }
            carStorage.addObject(new User("root", "root"));
        }
    }
}
