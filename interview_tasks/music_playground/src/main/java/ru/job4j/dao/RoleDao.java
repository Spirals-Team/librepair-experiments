package ru.job4j.dao;

import ru.job4j.model.Role;

public interface RoleDao extends CommonDao<Role, Long> {

    Long findLastId();
}
