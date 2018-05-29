package ru.job4j.pseudo;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PaintTest {
    // получаем в поле ссылку на стандартный вывод в консоль
    private final PrintStream stdout = System.out;
    // Буфер для хранения результата
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.out.println("Execute before method");
        // заменяем стандартный вывод, на вывод в память
        System.setOut(new PrintStream(out));
    }

    @After
    public void backOutput() {
        // Возвращаем вывод по умолчаниюю
        System.setOut(stdout);
        System.out.println("Execute after method");

    }

    @Test
    public void whenDrawShape() {
        // Выполняем действия пишущиее в консоль.
        new Paint().draw(new Square());
        // проверяем вычисления
        assertThat(new String(out.toByteArray()),
                is(new StringBuilder()
                        .append("++++")
                        .append("+  +")
                        .append("+  +")
                        .append("++++")
                        .append(System.lineSeparator())
                        .toString()));
    }

    @Test
    public void whenDrawTriangle() {
        new Paint().draw(new Triangle());
        assertThat(new String(out.toByteArray()),
                is(new StringBuilder()
                .append("+   ")
                .append("++  ")
                .append("+++ ")
                .append("++++")
                        .append(System.lineSeparator())
                .toString()));
    }
}
