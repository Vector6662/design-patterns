package ObserverPattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store implements Observable {
    private static final Logger log = LoggerFactory.getLogger(Store.class);

    private List<Observer> observers = new ArrayList<>();
    private Map<String,Product> products = new HashMap<>();
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    public void addNewProduct(String name,double price){
        Product p = new Product(name,price);
        products.put(name,p);
        observers.forEach(observer -> observer.onEvent(new Event<>("NewProduct", p)));
    }
    public void setProductPrice(String name, double price) {
        Product p = products.get(name);
        p.setPrice(price);
        // 通知观察者:
        observers.forEach(observer -> observer.onEvent(new Event<>("setProductPrice",p)));
    }
}
