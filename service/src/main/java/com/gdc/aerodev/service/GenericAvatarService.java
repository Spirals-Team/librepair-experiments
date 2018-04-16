package com.gdc.aerodev.service;

import com.gdc.aerodev.model.Avatar;

public interface GenericAvatarService extends GenericService {

    /**
     * Inserts avatar into database
     * @param avatar target
     * @return id of inserted avatar
     */
    Long uploadAvatar(Avatar avatar);

    /**
     * Gives {@code Avatar} by {@code User} id
     * @param id of avatar's owner
     * @return (0) {@code Avatar} or
     *         (1) {@code null}
     */
    Avatar getAvatar(Long id);
}
