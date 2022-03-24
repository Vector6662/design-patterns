package PubSubPattern.subscribers;

import PubSubPattern.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEventSubscriber implements Subscriber{
    private Logger logger= LoggerFactory.getLogger(MessageEventSubscriber.class);
    @Override
    public void doEvent(Result result) {
        logger.info("MESSAGE Event: {}",result);
    }
}
