package PubSubPattern.publishers;

import PubSubPattern.MessageCenter;
import PubSubPattern.Result;

import java.util.Date;

public class GoodBookPublisher extends Publisher{
    public GoodBookPublisher(MessageCenter messageCenter) {
        super(messageCenter);
    }

    @Override
    protected Result doDraw(String uId) {
        return new Result(uId,new Date(),"good book!");
    }
}
