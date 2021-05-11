package MutiThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class CompletableFutureTask {
    private static AtomicInteger count = new AtomicInteger(10);
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Integer>> resultList = new ArrayList<>();

        long start = System.currentTimeMillis();

        for(int i=0;i<10;i++){
            int finalI = i;
            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    int seconds = ThreadLocalRandom.current().nextInt(5);
                    TimeUnit.SECONDS.sleep(seconds);
                    System.out.println("task is completed! cost:" + seconds + "s left: " + count.decrementAndGet());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return finalI;
            },executorService);
            resultList.add(completableFuture);
        }
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(resultList.toArray(new CompletableFuture[0]));

    }
}
