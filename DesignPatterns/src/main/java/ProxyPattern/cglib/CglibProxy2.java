package ProxyPattern.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.transaction.annotation.Transactional;

/**
 * 方式二：优化，封装
 */
public class CglibProxy2 {
    public static void main(String[] args) {
        Student student1 = CglibProxy2.getProxy(Student.class);//是不是发现简洁多了，泛型是真的好用！
        student1.eat();

        Student student2 = new Student();
        Student student2Proxy = getProxy(student2);
        student2Proxy.eat();
    }
    public static <T> T getProxy(Class<T> clazz){
        //step1:获取Enhancer对象
        Enhancer enhancer = new Enhancer();
        //step2:设置父类，也是被代理类
        enhancer.setSuperclass(clazz);
        //step3:设置回调，在函数接口中
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            System.out.println("------代理新的------");
            System.out.println("begin intercept");
            Object o = proxy.invokeSuper(obj,args);
            /*
            * 下面这种方式会发生无限的循环调用，因为invoke中调用的任何原代理类方法，均会重新代理到invoke方法中
            * Object o = method.invoke(obj,args);
            * 这是因为参数obj传错了，obj对应的是。应该是target，即被代理对象，或说目标对象的实例。改成下面这种就没问题了：
            * Object o = method.invoke(target,args);
            * 见下面的个体Proxy方法
            * */
            System.out.println("end intercept");
            return o;
        });
        return (T) enhancer.create();
    }

    /**
     * 感觉这种方式常用一些，因为一般都是给现有对象添加一个代理对象
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T getProxy(T target){
        //step1:获取Enhancer对象
        Enhancer enhancer = new Enhancer();
        //step2:设置父类，也是被代理类
        enhancer.setSuperclass(target.getClass());
        //step3:设置回调，在函数接口中
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            System.out.println("------代理已有对象------");
            System.out.println("begin intercept");
            Object o = method.invoke(target,args);
            System.out.println("end intercept");
            return o;
        });
        return (T) enhancer.create();
    }
}
