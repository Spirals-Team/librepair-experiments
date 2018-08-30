package ru.job4j.userapp;

import java.util.List;

public class RoleDaoImpl implements RoleDao {

    @Override
    public List<Role> getAllRoles() {
        return DbStore.getInstance().getAllRoles();
    }
}
