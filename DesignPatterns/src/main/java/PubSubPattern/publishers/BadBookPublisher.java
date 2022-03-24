package PubSubPattern.publishers;

import PubSubPattern.MessageCenter;
import PubSubPattern.Result;

import java.util.Date;

public class BadBookPublisher extends Publisher{
    public BadBookPublisher(MessageCenter messageCenter) {
        super(messageCenter);
    }

    @Override
    protected Result doDraw(String uId) {
        return new Result(uId,new Date(),"bad book!");
    }
}
