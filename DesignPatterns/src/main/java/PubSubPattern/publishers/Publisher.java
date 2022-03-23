package PubSubPattern.publishers;

import PubSubPattern.EventType;
import PubSubPattern.MessageCenter;
import PubSubPattern.Result;

/**
 * 发布者，这里也涉及模板模式
 */
public abstract class Publisher {
    private MessageCenter messageCenter;
    public Publisher(MessageCenter messageCenter){
        this.messageCenter=messageCenter;
    }
    public Result draw(String uId){
        Result result = doDraw(uId);
        // TODO: 2022/3/18 通知，具体通知几个事件，按照具体业务而定
        messageCenter.notify(EventType.MQ,result);
        messageCenter.notify(EventType.MESSAGE,result);

        return result;
    }
    protected abstract Result doDraw(String uId);
}
