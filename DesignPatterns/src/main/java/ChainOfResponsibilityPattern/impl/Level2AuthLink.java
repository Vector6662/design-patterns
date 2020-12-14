package ChainOfResponsibilityPattern.impl;

import ChainOfResponsibilityPattern.AuthInfo;
import ChainOfResponsibilityPattern.AuthLink;
import ChainOfResponsibilityPattern.AuthService;

import java.text.ParseException;
import java.util.Date;

public class Level2AuthLink extends AuthLink {
    private Date beginDate = sf.parse("2020-09-01 00:00:00");
    private Date endDate = sf.parse("2020-12-31 23:59:59");

    /**
     * 该节点负责人信息
     *
     * @param levelUserId
     * @param levelUserName
     */
    public Level2AuthLink(String levelUserId, String levelUserName) throws ParseException {
        super(levelUserId, levelUserName);
    }

    @Override
    public AuthInfo doAuth(String userId, String orderId, Date authDate) {
        //每一个doAuth()方法都是只完成两件事，一是判断该层已经审核过，而是判断是否有下一个节点(appendNext)。判断为null的都直接返回AuthInfo对象

        /*step1：查询是否已经被该级别审核过，*/
        Date date = AuthService.queryAuthInfo(userId,orderId);
        if (date == null)
             return new AuthInfo("0001","单号" ,
                     orderId, "状态：待二级审批 负责人", levelUserName);

        /*step2：如果该级别审核过，判断是否有下一个节点*/
        AuthLink next = super.next();

        if (next == null)
            //没有下一个节点相当于已经审核完成了。具体有没有下一个节点就要看所在AuthLink是如何构造的了。这个例子中该链有三个节点，level1到3
            return new AuthInfo("0000", "单号",
                    orderId, "状态：二级审批完成负责人", "时间", sf.format(date), levelUserName);

        /*如果超出时间也返回*/
        if (authDate.before(beginDate)||authDate.after(endDate))
            return new AuthInfo("0000", "单号：",
                    orderId, "状态：二级审批完成负责人", "时间", sf.format(date), "审批人：", levelUserName);

        /*step3：若有，则进入下一个节点进行审核*/
        return next.doAuth(userId, orderId, authDate);
    }
}
