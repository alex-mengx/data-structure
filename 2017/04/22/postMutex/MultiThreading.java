package test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiThreading {
    private static final int maxNumberOfThreads = 4096;
    private static final int runs = 50;

    public static void main(String[] args)
            throws IOException, URISyntaxException, InterruptedException, ExecutionException {
        List<String> file = Files
                .readAllLines(Paths.get(MultiThreading.class.getResource("/resources/all.txt").toURI()));

        double[] timeCosts = new double[log2(maxNumberOfThreads) + 1];

        for (int i = 0; i < timeCosts.length; i++) {
            timeCosts[i] = runTasksForAverage(file, (int) Math.pow(2, i));
        }
        System.out.println();
        System.out.println("Test Results:");
        System.out.println("Enviroument: number of process is " + Runtime.getRuntime().availableProcessors()
                + ". Size of memory is : " + Runtime.getRuntime().totalMemory());
        System.out.println("Number of Threads    |    Average running time ");
        for (int i = 0; i < timeCosts.length; i++) {
            System.out.printf("  %4d               |            %.0f ms", (int) Math.pow(2, i), timeCosts[i]);
            System.out.println();
        }
    }

    private static double runTasksForAverage(List<String> file, int numberOfThreads) {
        return IntStream.range(0, runs).mapToLong(i -> runTask(file, numberOfThreads, i)).average().getAsDouble();
    }

    private static long runTask(List<String> file, int numberOfThreads, int trail) {
        List<List<String>> jobs = new LinkedList<>();

        int portion = file.size() / numberOfThreads;
        IntStream.range(0, numberOfThreads).forEach(i -> jobs.add(file.subList(i * portion, (i + 1) * portion)));

        List<Callable<Map<Character, Integer>>> tasks = IntStream.range(0, numberOfThreads)
                .mapToObj(i -> task(jobs.get(i), i)).collect(Collectors.toList());
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        long start = System.currentTimeMillis();

        CompletionService<Map<Character, Integer>> ecs = new ExecutorCompletionService<Map<Character, Integer>>(
                executor);
        for (Callable<Map<Character, Integer>> task : tasks)
            ecs.submit(task);

        HashMap<Character, Integer> finalResult = new HashMap<>();

        for (int i = 0; i < numberOfThreads; i++) {
            Map<Character, Integer> result;
            try {
                result = ecs.take().get();
                result.entrySet().forEach(e -> {
                    finalResult.computeIfPresent(e.getKey(), (key, value) -> value + e.getValue());
                    finalResult.computeIfAbsent(e.getKey(), (value) -> e.getValue());
                });
            } catch (InterruptedException | ExecutionException e3) {
                e3.printStackTrace();
            }
        }

        long cost = System.currentTimeMillis() - start;

        finalResult.entrySet().stream().sorted((e1, e2) -> e1.getKey().charValue() > e2.getKey().charValue() ? 1 : -1)
                .forEach(e -> System.out.println(e));

        executor.shutdown();

        System.out.println("Run with " + numberOfThreads + " threads. Current trail is: " + trail
                + ". Overall time cost is: " + cost + " milliseconds.");

        return cost;
    }

    private static Callable<Map<Character, Integer>> task(List<String> job, int trail) {
        return () -> {
            HashMap<Character, Integer> result = new HashMap<>();

            for (String str : job) {
                str.chars().forEach(c -> {
                    result.computeIfPresent((char) c, (key, value) -> ++value);
                    result.computeIfAbsent((char) c, (value) -> 1);
                });
            }
            return result;
        };
    }

    private static int log2(int bits) {
        if (bits == 0)
            return 0;
        return 31 - Integer.numberOfLeadingZeros(bits);
    }
}
