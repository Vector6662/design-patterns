package PubSubPattern.subscribers;

import PubSubPattern.Result;

/**
 * 订阅者
 */
public interface Subscriber {
    void doEvent(Result result);
}
