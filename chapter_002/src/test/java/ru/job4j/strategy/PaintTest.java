package ru.job4j.strategy;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * прочитал начало задания, о том что необходимо сделать рефакторинг этого класса и не подсматривал,
 * вот что получилось
 * @author Alexander Kaleganov
 * @version 1.0
 */

public class PaintTest {
    @Test
    public void testirovanieTRIANGLEpaint() {
        // получаем ссылку на стандартный вывод в консоль.
        PrintStream stdout = System.out;
        // проверяем результат вычисления
        assertThat(
                new String(byttearrReturn(new Triangle()).toByteArray()),
                is(new StringBuilder()
                        .append("  #  ")
                        .append(" ### ")
                        .append("#####")
                        .append(System.lineSeparator())
                        .toString()
                )
        );
        System.setOut(stdout);
    }

    @Test
    public void testirovanieSQUAREpaint() {
        PrintStream stdout = System.out;
        assertThat(
                new String(byttearrReturn(new Square()).toByteArray()), is(new StringBuilder()
                        .append("####")
                        .append("####")
                        .append("####")
                        .append("####")
                        .append(System.lineSeparator())
                        .toString()
                )
        );
        System.setOut(stdout);
    }

    public ByteArrayOutputStream byttearrReturn(Shape shape) {
        // Создаем буфур для хранения вывода.
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //Заменяем стандартный вывод на вывод в пямять для тестирования.
        System.setOut(new PrintStream(out));
        // выполняем действия пишушиее в консоль.
        new Paint().draw(shape);
        //возвращаем созданный и заполненный буфер
        return out;
    }
}