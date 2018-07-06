package ru.job4j.wait;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ThreadPoolTest {
    private PrintStream stdout = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(out));
    }

    @After
    public void backOut() {
        System.setOut(stdout);
    }

	/*
	На входе массив строк, некоторые элементы которого содержат
	строки с переносом на следующую строку.
	На выходе массив строк, каждый элемент которого содержит
	строку без переноса.
	*/
	private String[] setArray(String[] array) {
    	List<String> strings = new ArrayList<>();
		for (String item : array) {
			String[] cur = item.split("\n");
			if (cur.length > 1) {
				strings.addAll(Arrays.asList(cur));
			} else {
				strings.add(item);
			}
		}
		return strings.toArray(new String[strings.size()]);
	}

    @Test
    public void testPool() throws InterruptedException {
    	final int CORE = 2;
        ThreadPool pool = new ThreadPool(CORE);
        pool.startPoolWorker();
		Integer[] addItem = new Integer[] {1, 2, 3, 4, 5, 6};
		for (int item : addItem) {
			pool.add(new Work(item));
		}

		Thread.sleep(10000);
		pool.stopPool();

		/*
		Мы не можем предугадать поведение потоков(какой поток начнет работать, какой закончит и т.д)
		однако мы можем перенаправить стандартный поток вывода в ByteArrayOutputStream, затем получить массив
		строк и далее проанализировать данные, зная общие закономерности:
		1. При запуске программы(очередь задач пуста) в очередь может произойти только добавление задачи;
		2. Для задачи, которая выполняться (start), предшествует событие выталкивание из очереди (Poll);
		3. Завершиться (finish) может только та задача, которая перед этим начала выполняться (start);
		4. Каждый моменты времени количество запущенных задач не превышает количество потоков в пулле;
		5. Извлеченных задач из очереди, запущенных задач, выполнившихся задач должно быть столько,
		сколько быть добавлено в очередь;
		 */
        int nTask = 0; //количество задач обрабатываемых в данный момент (tasks которые совершили start)
        Set<Integer> offer = new HashSet<>(); //хранилище добавленных в очередь задач
        Set<Integer> poll = new HashSet<>(); //хранилище извлеченных задач из очереди
        Set<Integer> start = new HashSet<>(); //хранилище стартовавших задач
        Set<Integer> finish = new HashSet<>(); //хранилище задач, которые финишировали
		String[] strings = setArray(new String(out.toByteArray()).split(System.lineSeparator()));

        for (String item : strings) {
            String[] cond = item.split(" ");
			if (item.equals("Poll: null")) {
				continue;
			}
			if (cond[0].equals("Offer:")) {
				offer.add(Integer.parseInt(cond[2]));
			}
			if (cond[0].equals("Poll:")) {
				int cur = Integer.parseInt(cond[2]);
				poll.add(cur);
				assertTrue("Если был poll данной задачи, "
									+ "значит перед этим должен был "
									+ "быть offer",
									offer.contains(cur)
				);
			}
            if (cond[2].equals("start")) {
            	nTask++;
            	int cur = Integer.parseInt(cond[1].split("\\D+")[0]);
				start.add(cur);
				assertTrue("Если был start данной задачи, "
									+ "значит перед этим должен был быть poll",
									poll.contains(cur)
				);
				assertTrue("Количество выполняющихся задач "
									+ "в данный момент, не должно превышать "
									+ "число потоков в пуле",
									nTask <= CORE
				);
			}
			if (cond[2].equals("finish")) {
				nTask--;
				int cur = Integer.parseInt(cond[1].split("\\D+")[0]);
				finish.add(cur);
				assertTrue("Если был finish данной задачи, "
									+ "значит перед этим должен был "
									+ "быть start",
									finish.contains(cur)
				);
				assertTrue("Количество выполняющихся задач в данный "
									+ "момент, не может быть отрицательным числом",
									nTask >= 0
				);
			}
        }

        assertThat("В начальный момент может произойти "
							+ "только добавление задачи",
							strings[0].lastIndexOf("Offer:"),
							is(0)
		);
        assertTrue(Arrays.equals(offer.toArray(), addItem));
        assertTrue("Хранилище извлеченных из очереди задач "
							+ "соответствует массиву добавленных задач",
							Arrays.equals(poll.toArray(), addItem)
		);
        assertTrue("Хранилище запущенных задач соответствует "
							+ "массиву добавленных задач",
							Arrays.equals(start.toArray(), addItem)
		);
        assertTrue("Хранилище завершившихся задач соответствует "
							+ "массиву добавленных задач",
							Arrays.equals(finish.toArray(), addItem)
		);
    }
}
