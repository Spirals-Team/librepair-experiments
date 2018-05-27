package ru.job4j.storage;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final CarStor carStor = CarStor.INSTANCE;

    public void addBrand() {
        if (carStor.getbStor().getAll().size() == 0) {
            String[] brands = {"LADA", "Audi", "BMW", "Cadillac", "Chery", "Chevrolet", "Chrysler", "Citroen", "Daewoo",
                    "Dodge", "Fiat", "Ford", "Geely", "Honda", "Hyundai", "Infiniti", "Jaguar", "Jeep", "Kia", "LandRover",
                    "Lexus", "Lifan", "MINI", "Mazda", "Mercedes-Benz", "Mitsubishi", "Nissan", "Opel", "Peugeot",
                    "Porsche", "Renault", "Saab", "Skoda", "SsangYong", "Subaru", "Suzuki", "Toyota", "Volkswagen", "Volvo",
                    "GAZ", "UAZ", "Другая"};
            for (String st : brands) {
                carStor.getbStor().add(new Brand(st));
            }
        }
    }

    public void addModel() {
        if (carStor.getmStor().getAll().size() == 0) {
            List<Brand> list = carStor.getbStor().getAll();
            for (Brand brand : list) {
                carStor.getmStor().add(new Model("Другая", brand));
            }
            String brandName = "Audi";
            String[] models = {"A1", "A3", "A4", "A5", "A6", "A7", "A8", "Q3", "Q5", "Q7", "TT"};
            Brand brand = brandFindByName(brandName);
            for (String st : models) {
                carStor.getmStor().add(new Model(st, brand));
            }
            brandName = "BMW";
            models = new String[]{"1 Series", "2 Series", "3 Series", "4 Series", "5 Series", "6 Series", "7 Series",
                    "X1 Series", "X2 Series", "X3 Series", "X4 Series", "X5 Series", "X6 Series"};
            brand = brandFindByName(brandName);
            for (String st : models) {
                carStor.getmStor().add(new Model(st, brand));
            }
            brandName = "Mercedes-Benz";
            models = new String[]{"A Klasse", "B Klasse", "C Klasse", "E Klasse", "S Klasse", "CLA Klasse", "CLE Klasse",
                    "G Klasse", "GLA Klasse", "GLC Klasse", "GLE Klasse", "GLS Klasse"};
            brand = brandFindByName(brandName);
            for (String st : models) {
                carStor.getmStor().add(new Model(st, brand));
            }
        }
    }

    public void addRoot() {
        List<User> list = carStor.getuStor().getAll();
        User root = new User("root", new BCryptPasswordEncoder().encode("root"));
        root.setAdmin();
        if (list.size() == 0) {
            carStor.getuStor().add(root);
        } else {
            for (User user : list) {
                if (user.getLogin().equals("root")) {
                    return;
                }
            }
            carStor.getuStor().add(root);
        }
    }

    private Brand brandFindByName(String name) {
        for (Brand brand: carStor.getbStor().getAll()) {
            if (brand.getName().equals(name)) {
                return brand;
            }
        }
        return null;
    }
}