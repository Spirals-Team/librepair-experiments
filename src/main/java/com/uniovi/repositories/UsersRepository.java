package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.model.User;

public interface UsersRepository extends CrudRepository<User, Long> {

    User findByIdentificador(String dni);

}
