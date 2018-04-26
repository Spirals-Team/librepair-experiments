package ru.neustupov.votingforrestaurants;

import ru.neustupov.votingforrestaurants.model.AbstractBaseEntity;

public class AuthorizedUser {

    private static int id = AbstractBaseEntity.START_SEQ;

    public static int id() {
        return id;
    }

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }
}
