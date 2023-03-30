package ua.edu.cdu.vu.lab3;

import ua.edu.cdu.vu.lab3.storage.Storage;
import ua.edu.cdu.vu.lab3.worker.Consumer;
import ua.edu.cdu.vu.lab3.worker.Producer;

import java.util.Scanner;

public class Application {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        int storageSize = readInt();
        int itemsCount = readInt();

        Storage storage = new Storage(storageSize);
        Thread consumer = new Thread(new Consumer(storage, itemsCount));
        Thread producer = new Thread(new Producer(storage, itemsCount));

        consumer.start();
        producer.start();

        join(consumer, producer);
    }

    private static int readInt() {
        return SCANNER.nextInt();
    }

    private static void join(Thread ... threads) throws InterruptedException {
        for (var thread: threads) {
            thread.join();
        }
    }
}
