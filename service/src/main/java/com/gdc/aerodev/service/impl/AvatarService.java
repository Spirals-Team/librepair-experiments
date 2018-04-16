package com.gdc.aerodev.service.impl;

import com.gdc.aerodev.dao.postgres.PostgresAvatarDao;
import com.gdc.aerodev.dao.postgres.PostgresUserDao;
import com.gdc.aerodev.model.Avatar;
import com.gdc.aerodev.service.GenericAvatarService;
import com.gdc.aerodev.service.logging.LoggingService;

public class AvatarService implements GenericAvatarService, LoggingService {

    private final PostgresAvatarDao avDao;
    private final PostgresUserDao usrDao;

    public AvatarService(PostgresAvatarDao avDao, PostgresUserDao usrDao) {
        this.avDao = avDao;
        this.usrDao = usrDao;
    }


    @Override
    public Long uploadAvatar(Avatar avatar) {
        return null;
    }

    @Override
    public Avatar getAvatar(Long id) {

        return null;
    }
}

