package ObserverPattern;

/**
 * 观察者模式
 * 主要思路是：当一个对象的状态发生改变时，所有依赖于它的对象得到通知并作出更新
 */
public class Main {
    public static void main(String[] args) {
        Admin admin = new Admin();
        Customer customer = new Customer();
        Store store = new Store();

        store.addObserver(admin);
        store.addObserver(customer);
        store.addNewProduct("Li Ning",999.99);
        store.addNewProduct("Adidas",250);
        store.setProductPrice("Li Ning",666.66);

    }
}
