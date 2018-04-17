package ru.job4j.tracker;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ValidateInputTest {
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    @Before
    public void loadMem() {
        System.setOut(new PrintStream(this.mem));
    }

    @After
    public void loadSys() {
        System.setOut(this.out);
    }

    @Test
    public void whenInvalidInput() {
        ValidateInput input = new ValidateInput(new StubInput(new String[] {"invalid", "0"}));
        input.ask("Выберите пункт меню: ", new int[] {0, 1, 2, 3, 4, 5, 6});
        assertThat(this.mem.toString(), is(String.format("Введите корректные данные.%n")));
    }

    @Test
    public void whenInvalidMenuInput() {
        ValidateInput input = new ValidateInput(new StubInput(new String[] {"-1", "0"}));
        input.ask("Выберите пункт меню: ", new int[] {0, 1, 2, 3, 4, 5, 6});
        assertThat(this.mem.toString(), is(String.format("Введите значение меню от 0 до 6.%n")));
    }
}
