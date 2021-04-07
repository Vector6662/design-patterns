package TemplatePattern;

import TemplatePattern.group.JDNetMall;
import TemplatePattern.group.TaoBaoNetMall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * 模板模式
 * 核心是实现一个抽象类，如这里的JDNetMall和TaoBaoNetMall，这两个类只用实现抽象类中的部分方法，这些部分方法是个性化内容。
 * 然而对实现类的调用都只调用抽象类中的 **非** abstract方法，即这种模式的精髓在于：行动准则是固定的，只需要实现个性化的部分
 *
 * 同时我发现一个非常非常重要的点！！！不仅限于模板模式，只要是@Override过的方法，就不需要管它的调用了！因为抽象类
 * 自己的内部就会有相应的调用，接口类会有实现类进行相应的调用，实现这个接口或者抽象类的子类甚至是禁止关系自己@Override的
 * 类是如何被调用的，这是因为：单一职责。子类就只关注如何重写这个方法即可！
 * 灵感来源于：
 * https://bugstack.cn/itstack-demo-any/2020/01/20/%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90-%E6%89%8B%E5%86%99mybait-spring%E6%A0%B8%E5%BF%83%E5%8A%9F%E8%83%BD(%E5%B9%B2%E8%B4%A7%E5%A5%BD%E6%96%87%E4%B8%80%E6%AC%A1%E5%AD%A6%E4%BC%9A%E5%B7%A5%E5%8E%82bean-%E7%B1%BB%E4%BB%A3%E7%90%86-bean%E6%B3%A8%E5%86%8C%E7%9A%84%E4%BD%BF%E7%94%A8).html
 * 其中的 2.eanDefinitionRegistryPostProcessor 类注册
 * RegisterBeanFactory类只需要关注重写父类的postProcessBeanDefinitionRegistry()和postProcessBeanFactory()方法即可
 *
 */
public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        NetMall jd = new JDNetMall("liuhaodong","123456");
        NetMall taobao = new TaoBaoNetMall("dong", "123456");

        // TODO: 2020/11/13 尚未测试，因为HttpUtil还没写好
        logger.info("测试京东：{}",jd.generateGoodsPoster("..."));
        logger.info("测试淘宝：{}",taobao.generateGoodsPoster("..."));

    }
}
