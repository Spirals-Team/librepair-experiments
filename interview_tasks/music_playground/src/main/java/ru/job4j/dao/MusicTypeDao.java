package ru.job4j.dao;

import ru.job4j.model.MusicType;

public interface MusicTypeDao extends CommonDao<MusicType, Long> {

    Long findLastId();
}
