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
    public static <T> T getProxy(Class<T> clzz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clzz);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            System.out.println("begin intercept");
            Object o = proxy.invokeSuper(obj,args);
            System.out.println("end intercept");
            return o;
        });
        return (T) enhancer.create();
    }
}
