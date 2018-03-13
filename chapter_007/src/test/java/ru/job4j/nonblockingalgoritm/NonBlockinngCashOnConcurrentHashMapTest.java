package ru.job4j.nonblockingalgoritm;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

//1. Неблокирующий кеш [#4741]
//        Петр Арсентьев,  18.05.17 8:13
//        1. Необходимо сделать кеш для хранение моделей. в кеше должны быть методы
//        add, update delete,
//        2. Кеш должен работать на неблокирующих алгоритмах. - использовать ConcurrentHashMap
//        3. В кеше должна быть возможность проверять валидность данных. Например. Два пользователя
// прочитали данные task_1
//        первый пользователь изменил поле имя и второй сделал тоже самое. нужно перед обновлением
// данных проверить.
// что текущий пользователь не затер данные другого пользователя. если данные затерты то выбросить
// OplimisticException -
// такая реализация достигается за счет введение с модель поля version и перед обновлением данных
// проверять текущую версию и ту
// что обновляем и увеличивать на один каждый раз когда обновили. если версии не равны то кидать
// исключение.
//
//        Использовать метод https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html#computeIfPresent-K-java.util.function.BiFunction-


public class NonBlockinngCashOnConcurrentHashMapTest {

    @Test
    public void thenAddAnElementTooCashWhenYouCanGetItVersion() {

        Model model1 = new Model("task_1");

        NonBlockinngCashOnConcurrentHashMap nonBlockinngCashOnConcurrentHashMap = new NonBlockinngCashOnConcurrentHashMap(1000);

        nonBlockinngCashOnConcurrentHashMap.add(model1, model1.getVersion());

        int result = (int) nonBlockinngCashOnConcurrentHashMap.get(model1);

        int expected = model1.getVersion();

        assertThat(expected, is(result));

    }

    @Test
    public void thenUpdateElementInCashWhenYouCanGetItsVersionChanged() {

        Model model1 = new Model("task_1");

        NonBlockinngCashOnConcurrentHashMap nonBlockinngCashOnConcurrentHashMap = new NonBlockinngCashOnConcurrentHashMap(1000);

        nonBlockinngCashOnConcurrentHashMap.add(model1, model1.getVersion());


        int test = (int) nonBlockinngCashOnConcurrentHashMap.update(model1, model1.version);

        int result = (int) nonBlockinngCashOnConcurrentHashMap.get(model1);

        int expected = model1.getVersion(); //+ 1;

        if (nonBlockinngCashOnConcurrentHashMap.get(model1) == null) {
            System.out.println("asdf");
        }

        assertThat(expected, is(result));

    }

    @Test
    public void thenDeleleElementFromCashWhenThereIsNoElementWithSuchKey() {

        Model model1 = new Model("task_1");

        NonBlockinngCashOnConcurrentHashMap nonBlockinngCashOnConcurrentHashMap = new NonBlockinngCashOnConcurrentHashMap(1000);

        nonBlockinngCashOnConcurrentHashMap.add(model1, model1.getVersion());

        int size1 = nonBlockinngCashOnConcurrentHashMap.size();

        nonBlockinngCashOnConcurrentHashMap.delete(model1);

        int size0 = nonBlockinngCashOnConcurrentHashMap.size();

//
        assertThat(0, is(size0));
        assertThat(1, is(size1));

    }



}
