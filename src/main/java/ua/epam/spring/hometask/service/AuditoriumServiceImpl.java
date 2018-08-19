package ua.epam.spring.hometask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.repository.AuditoriumRepository;
import ua.epam.spring.hometask.util.exception.NotFoundException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Vladimir on 13.10.2017.
 */
@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    private AuditoriumRepository repository;

    @Autowired
    public void setRepository(AuditoriumRepository repository) {
        this.repository = repository;
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return repository.getAll().stream().collect(Collectors.toSet());
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        Auditorium result = repository.getAll()
                .stream()
                .filter(auditorium -> name.equals(auditorium.getName()))
                .findFirst()
                .orElse(null);

        if (result == null) {
            throw new NotFoundException("Auditorium with name=" + name + " wasn't found.");
        }
        return result;
    }
}
