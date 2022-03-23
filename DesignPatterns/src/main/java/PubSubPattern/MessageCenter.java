package PubSubPattern;

import PubSubPattern.subscribers.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发布订阅中心
 * 这是相比于观察者模式先进的地方，也适合面试的时候手撕
 */
public class MessageCenter {
    private Map<EventType, List<Subscriber>> subscribers=new HashMap<>();

    public MessageCenter(EventType ...type){
        for (EventType eventType : type) {
            subscribers.put(eventType,new ArrayList<>());
        }
    }

    public void publish(EventType type,Result message){

    }

    public void subscribe(EventType type,Subscriber subscriber){
        subscribers.get(type).add(subscriber);
    }

    public void unsubscribe(EventType type,Subscriber subscriber){
        subscribers.get(type).remove(subscriber);
    }

    public void notify(EventType type,Result result){
        for (Subscriber subscriber : subscribers.get(type)) {
            subscriber.doEvent(result);
        }
    }

}
