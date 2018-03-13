package ru.job4j.start;

public class ValidateInput extends ConsoleInput {
    /**
     * Создать класс ValidateInput, наследующий ConsoleInput. В нем переопределите метод ask таким образом,
     * что бы обрабатывались исключительные ситуации (при помощи блоков try { … } catch( … ) { … }).
     * Пример:

     При выборе пункта меню -1, программа должна вывести на экран сообщение о том, что необходимо выбрать значение из диапазона меню и запросить повторно ввод.

     При выборе пункта меню ‘a’, программа должна вывести на экран сообщение о том, что необходимо ввести корректное значение и запросить повторно ввод.
     * @param question
     * @return
     */
    @Override
    public String ask(String question) {
        return super.ask(question);
    }

    @Override
    public int ask(String question, int[] range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = super.ask(question, range);
                invalid = false;

//            } catch (Exception eee){eee.printStackTrace();}// {

            } catch (MenuOutException moe) {
              //  moe.printStackTrace();
                System.out.println("Please select key from menu.");
            } catch (ArrayIndexOutOfBoundsException nfe) {
                System.out.println("Please enter validate data again.");
            }
        } while (invalid);
        return value;
    }
}
