package ObserverPattern;

/**
 * 观察者模式
 * 主要思路是：当一个对象的状态发生改变时，所有依赖于它的对象得到通知并作出更新
 *
 * 两个顶级接口：Observable，被监听的类需要实现，如Store；  Observer，监听者需要实现。
 * Store中的两个方法，addNewProduct、setProductPrice中便是核心，将会调用被注册进入的observer们，通知他们事件发生了
 *
 */

// TODO: 2022/3/18 其实观察者模式还是一个强耦合的设计模式，因为在被观察者内部还得维护被观察者表，
//  而且是在业务代码中进行通知，这并不是一个很好的设计。
//  观察者模式的核心，也是比较有缺陷的地方，是需要被观察者显式地通知被观察者，也就是这里的observers.forEach(...).
//  以上参考这篇文章：https://juejin.cn/post/6978728619782701087
//  然而看了发布订阅者模式，发现其实还是有比较强的代码耦合性的，只不过是有一些优化。
public class Main {
    public static void main(String[] args) {
        Admin admin = new Admin();
        Customer customer = new Customer();

        Store store = new Store();

        //注册
        store.addObserver(admin);
        store.addObserver(customer);
        //调用并通知
        store.addNewProduct("Li Ning",999.99);
        store.addNewProduct("Adidas",250);
        store.setProductPrice("Li Ning",666.66);

    }
}
