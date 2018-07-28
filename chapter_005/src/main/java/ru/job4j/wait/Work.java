package ru.job4j.wait;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Work implements IWork {
    private int id;

    public Work(int id) {
        this.id = id;
    }

    @Override
    public void execute() throws InterruptedException {
        System.out.format("task %d: start\n", id);
        Thread.sleep(1000);
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.format("task %d: finish\n", id);
    }

    @Override
    public String toString() {
        return String.format("task %d", id);
    }
}
