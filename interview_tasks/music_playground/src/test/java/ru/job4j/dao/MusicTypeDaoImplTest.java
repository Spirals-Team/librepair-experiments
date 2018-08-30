package ru.job4j.dao;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.DbConnector;
import ru.job4j.model.MusicType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MusicTypeDaoImplTest {

    private MusicType rapGenre;
    private MusicTypeDaoImpl musicTypeDaoDao;

    @Before
    public void setUp() {
        DbConnector connector = DbConnector.getInstance();
        connector.cleanDB();
        connector.initDB();
        rapGenre = new MusicType(1L, "RAP");
        musicTypeDaoDao = new MusicTypeDaoImpl();
    }

    @Test
    public void findOne() {
        assertThat(musicTypeDaoDao.findOne(1L), is(rapGenre));
    }

    @Test
    public void findAll() {
        List<MusicType> roles = new ArrayList<>();
        roles.add(rapGenre);
        roles.add(new MusicType(2L, "ROCK"));
        roles.add(new MusicType(3L, "FUNK"));
        roles.add(new MusicType(4L, "JAZZ"));
        roles.add(new MusicType(5L, "CLASSICAL"));
        roles.add(new MusicType(6L, "METAL"));
        List<MusicType> all = musicTypeDaoDao.findAll();
        assertThat(all, hasSize(equalTo(6)));
        assertThat(all,
            containsInAnyOrder(roles.toArray(new MusicType[0])));
    }

    @Test
    public void create() {
        MusicType test = new MusicType("TEST");
        assertThat(musicTypeDaoDao.create(test), is(7L));
        assertThat(musicTypeDaoDao.findAll(), hasSize(equalTo(7)));
        assertThat(musicTypeDaoDao.findOne(7L).getGenre(), is(test.getGenre()));
    }

    @Test
    public void update() {
        MusicType user = musicTypeDaoDao.findOne(3L);
        boolean result = musicTypeDaoDao.update(new MusicType(3L, "SKA PUNK"));
        MusicType guest = musicTypeDaoDao.findOne(3L);
        assertThat(result, is(true));
        assertThat(musicTypeDaoDao.findAll(), hasSize(equalTo(6)));
        assertThat(user, not(guest));
        assertThat(guest.getGenre(), is("SKA PUNK"));
    }

    @Test
    public void delete() {
        boolean result = musicTypeDaoDao.delete(rapGenre);
        MusicType genre = musicTypeDaoDao.findOne(rapGenre.getId());
        assertThat(result, is(true));
        assertThat(musicTypeDaoDao.findAll(), hasSize(equalTo(5)));
        assertThat(genre.isEmpty(), is(true));
    }

    @Test
    public void deleteById() {
        boolean result = musicTypeDaoDao.deleteById(rapGenre.getId());
        MusicType genre = musicTypeDaoDao.findOne(rapGenre.getId());
        assertThat(result, is(true));
        assertThat(musicTypeDaoDao.findAll(), hasSize(equalTo(5)));
        assertThat(genre.isEmpty(), is(true));
    }
}