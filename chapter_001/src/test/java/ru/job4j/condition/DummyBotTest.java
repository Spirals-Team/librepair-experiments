package ru.job4j.condition;
/**
 * @autor Alexander Kaleganov
 * @version DummyBotTest 1.000
 * @since 28.01.2018 21:19
 */
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
public class DummyBotTest {

    DummyBot boot = new DummyBot();
    boolean pravda = true;

    /**
     * Я попробывал сократить тест до одного метода, можно ли так делать?
     */
    @Test
    public void whenGreetBot() {
        if (boot.answer("Привет, Бот.").equals(pravda)) {
            assertThat(boot.answer("Привет, Бот."), is("Привет, умник."));
        } else if (boot.answer("Пока.").equals(pravda)) {
            assertThat(boot.answer("Пока."), is("До скорой встречи."));
        } else {
            assertThat(boot.answer("Сколько будет 2+2 ?"), is("Это ставит меня в тупик. Спросите другой вопрос."));
        }
    }
}
