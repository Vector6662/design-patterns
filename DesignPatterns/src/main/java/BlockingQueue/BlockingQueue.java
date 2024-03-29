package BlockingQueue;

public interface BlockingQueue<T> {
    void put(T t) throws InterruptedException;
    T take() throws InterruptedException;
}
