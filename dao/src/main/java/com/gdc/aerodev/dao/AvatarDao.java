package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.Avatar;

import java.util.List;

public interface AvatarDao extends GenericDao<Avatar, Long> {

    @Deprecated
    @Override
    default List<Avatar> getAll(){return null;}

    @Deprecated
    @Override
    default Avatar getByName(String name){return null;}
}
