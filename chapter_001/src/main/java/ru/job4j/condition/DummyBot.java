package ru.job4j.condition;

/**
 * @author Daniil Emelyanov
 * @version $id$
 * @since  12/03/2018
 */
public class DummyBot {
    /**
     * Отвечает на вопросы.
     * @param question Вопрос от клиента.
     * @return Ответ.
     */
    public String answer(String question) {
        String answer = "Это ставит меня в тупик. Спросите другой вопрос.";
        if ("Привет бот".equals(question)) {
            answer = "Привет умник";
        } else if ("Пока".equals(question)) {
            answer = "До скорой встречи.";
            return answer;
        }
        return answer;
    }
}

