package ChainOfResponsibilityPattern;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这是授权类
 * authMap属性保存的是**已经**授权的信息
 * 将被审核的单子和审核者的uId拼接起来作为主键key，时间作为value
 *
 * 这里还有个重点：采用了单例模式，全局可以使用authMap中的信息
 */
public class AuthService {
    private static Map<String, Date> authMap = new ConcurrentHashMap<String, Date>();

    /**
     *
     * @param uId 认为这是一个会产生歧义的称呼，因为我看到实例代码中传入的其实都是levelUserId
     * @param orderId
     * @return
     */
    public static Date queryAuthInfo(String uId, String orderId){
        return authMap.get(uId.concat(orderId));
    }

    /**
     * 审核通过，将单号等信息放入审核已通过的字典中存放起来，方便查询（{@link AuthService#queryAuthInfo(String, String)}）
     * @param uId 这个称呼感觉有歧义，其实应该叫做levelUserId吧？
     * @param orderId
     */
    public static void auth(String uId, String orderId){
        authMap.put(uId.concat(orderId), new Date());
    }
}