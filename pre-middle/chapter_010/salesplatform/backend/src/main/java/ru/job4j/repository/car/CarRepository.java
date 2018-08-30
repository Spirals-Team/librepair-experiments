package ru.job4j.repository.car;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.model.car.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
