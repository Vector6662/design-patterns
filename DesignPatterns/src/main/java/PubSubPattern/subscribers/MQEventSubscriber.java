package PubSubPattern.subscribers;

import PubSubPattern.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQEventSubscriber implements Subscriber{
    private Logger logger= LoggerFactory.getLogger(MQEventSubscriber.class);
    @Override
    public void doEvent(Result result) {
        logger.info("MQ Event: {}",result);
    }
}
