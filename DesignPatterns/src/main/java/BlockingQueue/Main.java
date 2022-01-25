package BlockingQueue;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ConditionQueue queue = new ConditionQueue(10);

        for (int i=0;i<20;i++){
            int finalI = i;
            Runnable task1 = () -> {
                try {
                    queue.put(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            new Thread(task1).start();
        }
        for (int i=0;i<10;i++){
            Thread.sleep(1000);
            Runnable task2 = () -> {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            new Thread(task2).start();
        }

    }
}
