package ru.job4j.collections.frogway;

import java.util.Stack;

/**
 * Путь лягушки.
 * @author Hincu Andrei (andreih1981@gmail.com)on 30.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Way {
    /**
     * Первое дерево.
     */
    private final Pair tree1 = new Pair(4, 7, -1);
    /**
     * Второе дерево.
     */
    private final Pair tree2 = new Pair(13, 9, -1);

    /**
     * Главный метод программы.
     * @param y номер кольца откуда стартует лягушка.
     * @param x номер сектора откуда стартует лягушка.
     * @param yY номер кольца пункта назначения.
     * @param xX номер сектора пункта назначения.
     */
    public void start(int y, int x, int yY, int xX) {
        // Создаем массив.
        Pair[][] array = new Pair[10][16];
        //Устанавливаем деревья.
        array[7][4] = tree1;
        array[9][13] = tree2;
        // просчитываем все ходы из точки старта в любую точку массива.
        move(array, x - 1, y - 1, 0, 0);
        // Получаем обьект финишной точки с координатами и колличеством ходов.
        Pair n = array[yY - 1][xX - 1];
        StringBuilder sb = new StringBuilder();
        if (n == null) {
            sb.append("В данные координаты попасть невозможно.");
        } else {
            //формируем вывод.
            moveRevers(9, 10, array, sb);
            System.out.println(sb);
            System.out.println(String.format("В заданную точку лягушка попадет за : %d ходов.", n.getValue() - 1));
        }
    }

    /**
     * Метод формирует результаты для вывода в консоль.
     * @param x сектор пункта назначения.
     * @param y кольцо пункта назначения.
     * @param array массив значений.
     * @param sb строка для вывода.
     */
    public void  moveRevers(int x, int y, Pair[][]array, StringBuilder sb) {
        String line = System.lineSeparator();
        Stack<Pair> stack = new Stack<>();
        Pair pair = array[y - 1][x - 1];
        while (pair != null) {
            if (pair.getPrev() == null) {
                break;
            }
            stack.add(pair);
            pair = pair.getPrev();
        }
        while (!stack.empty()) {
            sb.append(stack.pop()).append(line);
        }
    }

    /**
     * Метод заполняет массив всеми возможными ходами из заданой точки старта.
     * @param array массив.
     * @param x номер сектора из которого стартуем.
     * @param y номер кольца из которого стартуем.
     * @param xX координата возможного хода по оси х.
     * @param yY координато возможного хода по оси у.
     */
    public void move(Pair[][] array, int x, int y, int xX, int yY) {
        // получаем обьект из заданных координат , если там пусто, то это точка старта
        // инициализируем ее новым объектом.
        Pair pair = array[y][x];
        if (pair == null) {
            pair = new Pair(x, y, 0);
            array[y][x] = pair;
        }
        // получаем колличество ходов до этой точки.
        int n = pair.getValue();
        /**
         * получаем координаты следующей точки.
         */
        x = x + xX;
        y = y + yY;
        if (x > 15) {
            x = x - 16;
        }
        //если лягушка выпрыгнула за десятое кольцо или за центр, возвращаемся.
        if (y > 9 || y < 0) {
            return;
        }
        // получаем обьект из точки куда прыгает лягушка.
        // если там пусто добовляем новый.
        Pair pair1 = array[y][x];
        if (pair1 == null) {
            pair1 = new Pair(x, y, 0, pair);
            array[y][x] = pair1;
        }
        // проверяем не дерево ли это если дерево то возвращаемся.
        if (pair1.getValue() == -1) {
            return;
        }
        //проверяем нет ли более короткого пути в данную точку, если есть возврашаемся.
        if (pair1.getValue() > 0 && pair1.getValue() < n) {
            return;
        }
        // если все хорошо то записываем в новую точку новый обьект и устанавливаем
        //колличество прыжков до него и ссылку на сектор откуда мы сюда пришли.
        pair1.setValue(n + 1);
        if (pair1.equals(pair)) {
            pair1.setPrev(null);
        } else {
            pair1.setPrev(pair);
        }
        move(array, x, y, 3, 0);
        move(array, x, y, 1, -2);
        move(array, x, y, 1, 2);
        move(array, x, y, 2, 1);
        move(array, x, y, 2, -1);
    }
}
