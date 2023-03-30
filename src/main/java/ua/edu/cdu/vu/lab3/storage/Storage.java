package ua.edu.cdu.vu.lab3.storage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Storage {

    private final BlockingQueue<Integer> queue;

    public Storage(int size) {
        this.queue = new ArrayBlockingQueue<>(size);
    }

    public void put(int value) {
        try {
            doWork(1);
            queue.put(value);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int get() {
        try {
            doWork(3);
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void doWork(int workSeconds) throws InterruptedException {
        TimeUnit.SECONDS.sleep(workSeconds);
    }
}
