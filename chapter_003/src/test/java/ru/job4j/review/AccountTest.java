package ru.job4j.review;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AccountTest {
    @Test
    public void whenDestAboveZeroThanTransferIsTrue() {
        Account user = new Account(10000, "M880882543");
        assertThat(user.transfer(user, 5000), is(true));
        Account two = new Account(500, "M880882543");
        System.out.println(user.hashCode());
        System.out.println(two.hashCode());
        System.out.println(user.equals(two));
    }
}