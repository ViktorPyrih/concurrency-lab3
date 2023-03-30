package ua.edu.cdu.vu.lab3.worker;

import ua.edu.cdu.vu.lab3.storage.Storage;

import java.util.logging.Logger;
import java.util.stream.IntStream;

public record Consumer(Storage storage, int itemsToConsume) implements Runnable {

    private static final Logger log = Logger.getLogger(Consumer.class.getName());

    @Override
    public void run() {
        log.info("Consumer-%d started!".formatted(Thread.currentThread().getId()));
        IntStream.range(0, itemsToConsume)
                .forEach(i -> {
                    int item = storage.get();
                    log.info("Consumer-%d consumed item: %d".formatted(Thread.currentThread().getId(), item));
                });
        log.info("Consumer-%d finished!".formatted(Thread.currentThread().getId()));
    }
}
