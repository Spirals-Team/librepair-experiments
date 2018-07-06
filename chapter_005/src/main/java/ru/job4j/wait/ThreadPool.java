package ru.job4j.wait;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ThreadPool {
    private final SimpleBlockingQueue<Work> queue = new SimpleBlockingQueue<>(10);
    private PoolWorker[] threads;
    private int core;

    public ThreadPool(int core) {
        this.core = core;
        threads = new PoolWorker[core];
    }

    public void startPoolWorker() {
        for (int i = 0; i < core; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void add(Work work) {
        queue.offer(work);
    }

	public void stopPool() {
		for (PoolWorker item : threads) {
			item.interrupt();
		}
	}

    private class PoolWorker extends Thread {
        @Override
        public void run() {
        	while (!isInterrupted()) {
        		Work work = queue.poll();
        		if (work != null) {
					work.run();
				}
			}
        }
    }
}
