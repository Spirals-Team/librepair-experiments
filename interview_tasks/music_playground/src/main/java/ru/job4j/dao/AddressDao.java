package ru.job4j.dao;

import ru.job4j.model.Address;

public interface AddressDao extends CommonDao<Address, Long> {

    Long findLastId();
}
