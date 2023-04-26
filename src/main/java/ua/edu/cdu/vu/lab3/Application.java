package ua.edu.cdu.vu.lab3;

import ua.edu.cdu.vu.lab3.storage.Storage;
import ua.edu.cdu.vu.lab3.worker.Consumer;
import ua.edu.cdu.vu.lab3.worker.Producer;

import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Application {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        int storageSize = readInt();
        int itemsCount = readInt();
        int workersCount = readInt();

        Storage storage = new Storage(storageSize);
        Thread[] consumers = createWorkers(workersCount, () -> new Consumer(storage, itemsCount));
        Thread[] producers = createWorkers(workersCount, () -> new Producer(storage, itemsCount));

        start(consumers);
        start(producers);

        join(consumers);
        join(producers);
    }

    private static int readInt() {
        return SCANNER.nextInt();
    }

    private static void start(Thread ... threads) {
        for (var thread: threads) {
            thread.start();
        }
    }

    private static void join(Thread ... threads) throws InterruptedException {
        for (var thread: threads) {
            thread.join();
        }
    }

    private static Thread[] createWorkers(int workersCount, Supplier<Runnable> workerSupplier) {
        return IntStream.range(0, workersCount)
                .mapToObj(i -> new Thread(workerSupplier.get()))
                .toArray(Thread[]::new);
    }
}
