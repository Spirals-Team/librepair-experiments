package ru.job4j.dao;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.DbConnector;
import ru.job4j.dto.CatalogDto;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CatalogDaoImplTest {

    private CatalogDto defaultCatalog;
    private CatalogDaoImpl catalogDao;

    @Before
    public void setUp() {
        DbConnector connector = DbConnector.getInstance();
        connector.cleanDB();
        connector.initDB();
        defaultCatalog = new CatalogDto(1L, 1L, 1L);
        catalogDao = new CatalogDaoImpl();
    }

    @Test
    public void findOne() {
        assertThat(catalogDao.findOne(1L), is(defaultCatalog));
    }

    @Test
    public void findAll() {
        List<CatalogDto> catalog = new ArrayList<>();
        catalog.add(defaultCatalog);
        List<CatalogDto> all = catalogDao.findAll();
        assertThat(all, hasSize(equalTo(1)));
        assertThat(all,
            containsInAnyOrder(catalog.toArray(new CatalogDto[0])));
    }

    @Test
    public void create() {
        CatalogDto test = new CatalogDto(1L, 2L);
        assertThat(catalogDao.create(test), is(2L));
        assertThat(catalogDao.findAll(), hasSize(equalTo(2)));
        CatalogDto newCatalog = catalogDao.findOne(2L);
        assertThat(newCatalog.getUserId(), is(test.getUserId()));
        assertThat(newCatalog.getGenreId(), is(test.getGenreId()));
    }

    @Test
    public void update() {
        CatalogDto prevCatalog = catalogDao.findOne(1L);
        boolean result = catalogDao.update(new CatalogDto(1L, 1L, 2L));
        CatalogDto currentCatalog = catalogDao.findOne(1L);
        assertThat(result, is(true));
        assertThat(catalogDao.findAll(), hasSize(equalTo(1)));
        assertThat(prevCatalog, not(currentCatalog));
        assertThat(currentCatalog.getUserId(), is(1L));
        assertThat(currentCatalog.getGenreId(), is(2L));
    }

    @Test
    public void delete() {
        boolean result = catalogDao.delete(defaultCatalog);
        CatalogDto catalog = catalogDao.findOne(defaultCatalog.getId());
        assertThat(result, is(true));
        assertThat(catalogDao.findAll(), is(emptyCollectionOf(CatalogDto.class)));
        assertThat(catalog.isEmpty(), is(true));
    }

    @Test
    public void deleteById() {
        boolean result = catalogDao.deleteById(defaultCatalog.getId());
        CatalogDto catalog = catalogDao.findOne(defaultCatalog.getId());
        assertThat(result, is(true));
        assertThat(catalogDao.findAll(), is(emptyCollectionOf(CatalogDto.class)));
        assertThat(catalog.isEmpty(), is(true));
    }
}