package PubSubPattern;

import PubSubPattern.publishers.BadBookPublisher;
import PubSubPattern.publishers.GoodBookPublisher;
import PubSubPattern.publishers.Publisher;
import PubSubPattern.subscribers.MQEventSubscriber;
import PubSubPattern.subscribers.MessageEventSubscriber;

/**
 * 发布订阅模式
 * 相较于观察者模式，就是把发布订阅过程给封装了一下
 */
public class Main {
    public static void main(String[] args) {
        MessageCenter center=new MessageCenter(EventType.MESSAGE,EventType.MQ);
        center.subscribe(EventType.MQ,new MQEventSubscriber());
        center.subscribe(EventType.MESSAGE,new MessageEventSubscriber());

        Publisher publisher1=new GoodBookPublisher(center);
        Publisher publisher2=new BadBookPublisher(center);
        publisher1.draw("123");
        publisher2.draw("456");

    }
}
