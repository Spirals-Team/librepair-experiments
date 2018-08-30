package ru.job4j.repository.car.parts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.model.car.parts.Manufacture;

public interface ManufactureRepository extends JpaRepository<Manufacture, Long> {
}
