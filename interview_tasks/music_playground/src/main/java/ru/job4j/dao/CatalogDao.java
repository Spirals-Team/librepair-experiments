package ru.job4j.dao;

import ru.job4j.dto.CatalogDto;

public interface CatalogDao extends CommonDao<CatalogDto, Long> {

    Long findLastId();
}
