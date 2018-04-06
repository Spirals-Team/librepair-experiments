package com.m2dl.dlmovie.users.repositories;

import com.m2dl.dlmovie.users.domain.User;

public interface UserRepository {
    User save(User user);
}
