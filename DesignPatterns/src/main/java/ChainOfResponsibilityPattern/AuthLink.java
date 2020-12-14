package ChainOfResponsibilityPattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class AuthLink {
    protected Logger logger = LoggerFactory.getLogger(AuthLink.class);
    protected SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected String levelUserId;
    protected String levelUserName;
    private  AuthLink next;

    /**
     * 该节点负责人信息
     * @param levelUserId
     * @param levelUserName
     */
    public AuthLink(String levelUserId, String levelUserName) {
        this.levelUserId = levelUserId;
        this.levelUserName = levelUserName;
    }
    public AuthLink next(){
        //这里是在测试this这个知识点的，其实用抽象类来理解这个知识点方便些，毕竟抽象类是不能直接实例化的，于是这个this肯定就是指的它的实现者：子类
        System.out.println("测试一下this保留字的准确含义："+this.getClass().getName());
        return this.next;
    }
    public AuthLink appendNext(AuthLink authLink){
        System.out.println(this.getClass().getName());
        this.next=authLink;
        return this;  //注意这里是链式编程
    }

    /**
     * 这里面写具体的业务逻辑<p>
     * 这里其实没有审批的动作其实都在{@link AuthService#auth(String, String)}里完成的<p></p>
     * @param userId
     * @param orderId
     * @param authDate
     * @return
     */
    public abstract AuthInfo doAuth(String userId, String orderId, Date authDate);


}
