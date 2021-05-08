package ProxyPattern.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanFactory;

import java.lang.reflect.Method;

public class CglibProxy {
    public static <T> T getProxy(Class<T> clazz){
        //step1:获取Enhancer对象
        Enhancer enhancer = new Enhancer();
        //step2:设置父类，也是被代理类
        enhancer.setSuperclass(clazz);
        //step3:设置回调，在函数接口中
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            System.out.println("begin intercept");
            Object o = proxy.invokeSuper(obj,args);
            /*
            * 下面这种方式会发生无限的循环调用，因为invoke中调用的任何原代理类方法，均会重新代理到invoke方法中
            * Object o = method.invoke(obj,args);
            * 这是因为参数obj传错了，obj对应的是。应该是target，即被代理对象，或说目标对象的实例。改成下面这种就没问题了：
            * Object o = method.invoke(target,args);
            * */
            System.out.println("end intercept");
            return o;
        });
        return (T) enhancer.create();
    }
}
