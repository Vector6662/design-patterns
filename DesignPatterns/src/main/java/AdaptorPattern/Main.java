package AdaptorPattern;


public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(new CallableAdaptor<>(() -> new Person(1234, "大哥")));
        t1.start();
    }
}
