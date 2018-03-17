package ru.skorikov;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 05.03.18
 * @ version: java_kurs_standart
 */
public class Hero implements Runnable {
    /**
     * Имя героя.
     */
    private final String name;
    /**
     * Поле для героя.
     */
    private final Board board;

    /**
     * Создетель героя.
     *
     * @param name  имя.
     * @param board поле.
     */
    Hero(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    /**
     *
     */
    private final Random random = new Random();

    /**
     * Пробуем занять начальную позицию.
     *
     * @return ячейку с координатами стартовой позиции.
     */
    private Cell startPosition() {
        //стратовая ячейка
        Cell start = null;
        int boardSize = board.getBoard().length;
        Random random = new Random();
        boolean isCreate = false;
        //пока не получим лок ячейки доски
        while (!isCreate) {
            int x = random.nextInt(boardSize - 1);
            int y = random.nextInt(boardSize - 1);
            if (board.getBoard()[x][y].tryLock()) {
                start = new Cell(x, y);
                isCreate = true;
            }
        }
        return start;
    }

    /**
     * Генерация случайной клетки.
     *
     * @param cell стартовая ячейка.
     * @return новая ячейка.
     */
    private Cell createRandomCell(Cell cell) {
        //Cell randomCell = null;
        //случайное число от 0 до 4
        int nextCell = random.nextInt(5 - 1);
        int tempX = cell.getX();
        int tempY = cell.getY();
        switch (nextCell) {
            //клетка вверх
            case 0:
                tempX -= 1;
                break;
            //клетка вправо
            case 1:
                tempY += 1;
                break;
            //клетка вниз
            case 2:
                tempX += 1;
                break;
            //клетка влево
            case 3:
                tempY -= 1;
                break;
            default:
                break;
        }
        return new Cell(tempX, tempY);
    }

    /**
     * Следующий шаг.
     * Будем получать случайную ячейку сверху, снизу, слева или справа
     * и проверять на выход за пределы массива.
     * Если не вышли за пределы - пробуем локировать, разлокировать старую
     * и вернуть координаты новой.
     *
     * @param position стартовая позиция
     * @return конечная позиция.
     * @throws InterruptedException исключение.
     */
    private Cell nextStep(Cell position) throws InterruptedException {
        boolean isNext = false;
        Cell next = null;
        //пока не найдем куда ходить
        while (!isNext) {
            next = createRandomCell(position);
            //если не вышли за нраницы поля
            if (next.getX() >= 0 && next.getX() < board.getBoard().length - 1
                    && next.getY() >= 0 && next.getY() < board.getBoard().length - 1) {
                //если получилось захватить лок
                if (board.getBoard()[next.getX()][next.getY()].tryLock(500, TimeUnit.MILLISECONDS)) {
                    board.getBoard()[position.getX()][position.getY()].unlock();
                    isNext = true;
                }
            }
        }
        return next;
    }


    /**
     * Тут герой будет двигаться.
     * Каждую секунду на новую клетку.
     */
    @Override
    public synchronized void run() {
        Thread.currentThread().setName(name);
        //Получаем позицию
        Cell startPosition = startPosition();
        //пока не прервали.
        //спим секунду
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
                startPosition = nextStep(startPosition);
                System.out.print(Thread.currentThread().getName());
                System.out.println(" Cell " + startPosition.getX() + startPosition.getY());
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " отмучился");
            Thread.currentThread().interrupt();
        }
    }

}
