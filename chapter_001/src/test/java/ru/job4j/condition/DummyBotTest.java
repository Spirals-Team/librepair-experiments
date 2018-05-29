package ru.job4j.condition;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Daniil Emelyanov
 * @version  $Id@
 * @since 12/03/18
 */
public class DummyBotTest {
    @Test
    public void whenGreetBot() {
        DummyBot bot = new DummyBot();
        assertThat(bot.answer("Привет бот"), is("Привет умник"));
    }

    @Test
    public void whenBuyBot() {
        DummyBot bot = new DummyBot();
        assertThat(bot.answer("Пока"), is("До скорой встречи."));
    }

    @Test
    public void whenInknowBot() {
        DummyBot bot = new DummyBot();
        assertThat(bot.answer("Сколько будет 2 + 2?"), is("Это ставит меня в тупик. Спросите другой вопрос."));

    }
}


