package co.spraybot.helpers;

import co.spraybot.DefaultUser;
import co.spraybot.User;
import com.github.javafaker.Faker;

public interface FictionalUsersProvider {

    default User randomUser() {
        String name = Faker.instance().name().username();
        return new DefaultUser(name, name);
    }

    default User johnnyAwesome() {
        return new DefaultUser("johnny-awesome", "johnny");
    }

    default User pamPoovey() {
        return new DefaultUser("pam-poovey", "pam");
    }

    default User sterlingArcher() {
        return new DefaultUser("sterling-archer", "sterling");
    }

}
