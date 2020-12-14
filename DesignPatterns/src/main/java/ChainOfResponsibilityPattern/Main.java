package ChainOfResponsibilityPattern;

import ChainOfResponsibilityPattern.impl.Level1AuthLink;
import ChainOfResponsibilityPattern.impl.Level2AuthLink;
import ChainOfResponsibilityPattern.impl.Level3AuthLink;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * 责任链模式
 * 例子来源于小博哥的重学设计模式那本电子书，但我不认为这是一个很好地例子，大概是自己的段位太低，难以理解其中的奥义
 *
 * 廖雪峰老师爹解释提供了责任链模式的另一种实现方式：https://www.liaoxuefeng.com/wiki/1252599548343744/1281319474561057
 * 相比于本例，他多引入了一个HandlerChain类，作用是统一管理所有的节点，相当于把节点集中管理。但两种方式本质都是一样的。
 */
public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class );
    public static void main(String[] args) throws ParseException {
        AuthLink authLink = new Level3AuthLink("1000013", "王工")
                .appendNext(new Level2AuthLink("1000012", "张经理"))
                .appendNext(new Level1AuthLink("1000011", "段总"));
        logger.info("测试结果：{}", JSON.toJSONString(authLink.doAuth("东东枪", "201613140210", new Date())));

        //模拟三级负责人
        AuthService.auth("1000013", "201613140210");  //这里才是真正进行授权的地方，而不是AuthLink中
        logger.info("测试结果:{}", "模拟三级负责人审批，王工");
        logger.info("测试结果：{}", JSON.toJSONString(authLink.doAuth("东东枪", "201613140210", new Date())));

        //模拟二级负责人
        AuthService.auth("1000013", "201613140210");  //这里才是真正进行授权的地方，而不是AuthLink中
        logger.info("测试结果:{}", "模拟二级负责人审批，张经理");
        logger.info("测试结果：{}", JSON.toJSONString(authLink.doAuth("东东枪", "201613140210", new Date())));

        //模拟一级负责人
        AuthService.auth("1000013", "201613140210");  //这里才是真正进行授权的地方，而不是AuthLink中
        logger.info("测试结果:{}", "模拟一级负责人审批，段总");
        logger.info("测试结果：{}", JSON.toJSONString(authLink.doAuth("东东枪", "201613140210", new Date())));


    }
}
