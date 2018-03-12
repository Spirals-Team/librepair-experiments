package com.sismics.docs.core.util;

import com.sismics.docs.core.constant.AclTargetType;
import com.sismics.docs.core.dao.jpa.GroupDao;
import com.sismics.docs.core.dao.jpa.UserDao;
import com.sismics.docs.core.model.jpa.Group;
import com.sismics.docs.core.model.jpa.User;

/**
 * Security utilities.
 *
 * @author bgamard
 */
public class SecurityUtil {
    /**
     * Get an ACL target ID from an object name and type.
     *
     * @param name Object name
     * @param type Object type
     * @return Target ID
     */
    public static String getTargetIdFromName(String name, AclTargetType type) {
        switch (type) {
            case USER:
                UserDao userDao = new UserDao();
                User user = userDao.getActiveByUsername(name);
                if (user != null) {
                    return user.getId();
                }
            case GROUP:
                GroupDao groupDao = new GroupDao();
                Group group = groupDao.getActiveByName(name);
                if (group != null) {
                    return group.getId();
                }
        }

        return null;
    }
}
