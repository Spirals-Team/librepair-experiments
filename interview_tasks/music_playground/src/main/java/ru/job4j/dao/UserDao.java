package ru.job4j.dao;

import ru.job4j.dto.UserDto;

public interface UserDao extends CommonDao<UserDto, Long> {

    Long findLastId();
}
