package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 28.12.17
 * @ version: java_kurs_standart
 */
public class RoleTest {
    /**
     * Пробуем получить Role.
     *
     * @throws Exception исключение.
     */
    @Test
    public void getRole() throws Exception {
        Role role = new Role("Str1", "Str2");
        String test = "Str2";
        Assert.assertThat(role.getRole(), is(test));
    }

    /**
     * Пробуем задать Role.
     *
     * @throws Exception исключениеи.
     */
    @Test
    public void setRole() throws Exception {
        Role role = new Role("Str1", "Str");
        role.setRole("Str2");
        String test = "Str2";

        Assert.assertThat(role.getRole(), is(test));
    }

}