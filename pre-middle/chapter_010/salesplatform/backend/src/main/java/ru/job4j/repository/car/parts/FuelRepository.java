package ru.job4j.repository.car.parts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.model.car.parts.Fuel;

public interface FuelRepository extends JpaRepository<Fuel, Long> {
}
