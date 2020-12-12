package ChainOfResponsibilityPattern.impl;

import ChainOfResponsibilityPattern.AuthInfo;
import ChainOfResponsibilityPattern.AuthLink;
import ChainOfResponsibilityPattern.AuthService;

import java.util.Date;

public class Level1AuthLink extends AuthLink {
    public Level1AuthLink(String levelUserId, String levelUserName) {
        super(levelUserId, levelUserName);
    }

    @Override
    public AuthInfo doAuth(String userId, String orderId, Date authDate) {
        Date date = AuthService.queryAuthInfo(levelUserId, orderId);
        if (date == null)
            return new AuthInfo("0001", "单号：", orderId,
                    "状态：等待一级审批负责人", levelUserName); //审核结束，将审核过程信息汇总后返回

        AuthLink next = super.next();

        if (next == null)
            return new AuthInfo("0000","单号：", orderId
                    , "状态：一级审批完成负责人", "时间：", sf.format(date), "审批人：", levelUserName);

        return next.doAuth(userId, orderId, authDate);

    }
}
