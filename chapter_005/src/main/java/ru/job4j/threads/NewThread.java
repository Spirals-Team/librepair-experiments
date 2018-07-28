package ru.job4j.threads;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
class NewThread implements Runnable {
    private Searchable current;
    private Thread thread;

    public NewThread(Searchable current) {
        this.current = current;
        String name = current.getName();
        System.out.println(String.format("Запуск потока: %s", name));
        thread = new Thread(this, name);
        thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        try {
            current.search();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
