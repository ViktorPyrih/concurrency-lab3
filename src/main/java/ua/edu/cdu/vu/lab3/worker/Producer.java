package ua.edu.cdu.vu.lab3.worker;

import ua.edu.cdu.vu.lab3.storage.Storage;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public record Producer(Storage storage, int itemsToProduce) implements Runnable {

    private static final Logger log = Logger.getLogger(Producer.class.getName());

    @Override
    public void run() {
        log.info("Producer-%d started!".formatted(Thread.currentThread().getId()));
        IntStream.range(0, itemsToProduce)
                .forEach(i -> {
                    int item = ThreadLocalRandom.current().nextInt();
                    storage.put(item);
                    log.info("Producer-%d produced item: %d".formatted(Thread.currentThread().getId(), item));
                });
        log.info("Producer-%d finished!".formatted(Thread.currentThread().getId()));
    }
}
