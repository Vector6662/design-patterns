package ObserverPattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Customer implements Observer {
    private static final Logger log = LoggerFactory.getLogger(Store.class);

    @Override
    public <T> void onEvent(Event<T> event) {
        String name = event.getEvent();
        T t = event.getObj();
        /*
        业务逻辑...
         */
        log.info("customer:{}  {}",name,t);
    }
}
