package ObserverPattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Admin implements Observer {
    private static final Logger log = LoggerFactory.getLogger(Admin.class);

    @Override
    public <T> void onEvent(Event<T> event) {
        String name = event.getEvent();
        T t = event.getObj();
        // TODO: 2021/4/7  业务逻辑
        log.info("admin:{}  {}",name,t);
    }
}
