package main;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 27.11.2021
 * 20. Java IO
 *
 * @author Ludmila Kondratyeva (National Aeronautics and Space Administration)
 * @version v1.0
 */

public class Main {
    public static long array[];
    public static long sums[];
    public static long sum;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService);
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int numbersCount = scanner.nextInt();
        int threadsCount = scanner.nextInt();
        if(numbersCount < threadsCount) {
            threadsCount = numbersCount;
        } else if(threadsCount == 0) {
            threadsCount = 1;
        }
        array = new long[numbersCount];
        sums = new long[threadsCount];

        // заполняем случайными числами
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }
        long realSum = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            realSum += array[i];
        }
        long stopTime = System.currentTimeMillis();

        // для 2 000 000 -> 98996497, 98913187
        System.out.println("single thead summ: " + realSum);
        System.out.println(String.format("Длительность суммирования в однопоточном режиме %s milliseconds", stopTime - startTime));

        // TODO: реализовать работу с потоками
        int parts = array.length / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            int inclisive = i * parts;
            int exclusive = parts * (i + 1);
            completionService.submit(() -> new SumThread(inclisive, exclusive).summ());
        }
        try {
            startTime = System.currentTimeMillis();
            for (int i = 0; i < threadsCount; i++) {
                Future<Long> poll = completionService.take();
                sums[i] = poll.get();
            }
            long byThreadSum = 0;
            for (int i = 0; i < threadsCount; i++) {
                byThreadSum += sums[i];
            }
            stopTime = System.currentTimeMillis();

            System.out.println("multithread summ: " + byThreadSum);
            System.out.println(String.format("Длительность суммирования в многопоточном режиме %s milliseconds", stopTime - startTime));
        } catch (InterruptedException | ExecutionException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        executorService.shutdownNow();

        /**
         *  input:  100000000 4
         *  single thead summ: 4949973896
         *  Длительность суммирования в однопоточном режиме 51 milliseconds
         *  multithread summ: 4949973896
         *  Длительность суммирования в многопоточном режиме 38 milliseconds
         */
    }

    public static class SumThread {
        private int position;
        private int from;
        private int to;

        public SumThread(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public long summ() {
            long sum = 0;
            for (int i = from; i < to; i++) {
                sum += array[i];
            }
            return sum;
        }
    }
}
