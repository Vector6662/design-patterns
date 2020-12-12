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
        /*step1：查询是否已经被该级别审核过，*/
        Date date = AuthService.queryAuthInfo(userId,orderId);
        if (date == null)
             return new AuthInfo("0001","单号" ,
                     orderId, "状态：待二级审批 负责人", levelUserName);

        /*step2：如果审核过，判断是否有下一个节点*/
        AuthLink next = super.next();

        if (next == null)
            return new AuthInfo("0000", "单号",
                    orderId, "状态：二级审批完成负责人", "时间", sf.format(date), levelUserName);

        if (authDate.before(beginDate)||authDate.after(endDate))
            return new AuthInfo("0000", "单号：",
                    orderId, "状态：二级审批完成负责人", "时间", sf.format(date), "审批人：", levelUserName);

        /*step3：若有，则进入下一个节点进行审核*/
        return next.doAuth(userId, orderId, authDate);
    }
}
