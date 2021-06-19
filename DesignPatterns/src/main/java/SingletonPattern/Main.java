package SingletonPattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本例首先实现一个双重检查形式的单例模式，主要参考https://javadoop.com/post/java-memory-model
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        for (int i=0;i<100;i++){new Thread(new Runnable() {
                @Override
                public void run() {
                    Singleton1 singleton1 = Singleton1.getInstance();
                    logger.info("thread: {}, singleton: {}",Thread.currentThread(),singleton1);
                }
            }).start();
        }
    }

}
