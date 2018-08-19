package ua.epam.spring.hometask.repository;

import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Collection;

/**
 * Created by Vladimir on 13.10.2017.
 */
public interface AuditoriumRepository {
    Collection<Auditorium> getAll();
}
