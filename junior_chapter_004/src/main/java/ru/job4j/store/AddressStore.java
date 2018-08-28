package ru.job4j.store;

import java.util.List;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 20.08.2018.
 * @version 1.0.
 * @since 0.1.
 */
public interface AddressStore {
    public List<String> findAllCountries();

    public List<String> findCitiesByCountry(String name);
}
