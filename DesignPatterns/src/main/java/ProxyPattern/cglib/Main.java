package ProxyPattern.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 用cglib实现动态代理
 * 该方式的关键类其实是Enhancer，而不是MethodInterceptor
 */
public class Main {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Student.class);
        //注意这里有个细节，用lambda表达式但是setCallback()的参数接受的是Callback类型，虽然MethodInterceptor是其子类但是这里还是要强转一下
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("before invoke");
            Object obj =  methodProxy.invokeSuper(o,objects);//为什么是invokeSuper()而不是直接invoke？这是因为这个enhancer类是作为我的目标类的子类的回调
            /*或者采用另一种方式也是可以的，参考https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483954&idx=1&sn=b34e385ed716edf6f58998ec329f9867&chksm=ebd74333dca0ca257a77c02ab458300ef982adff3cf37eb6d8d2f985f11df5cc07ef17f659d4&scene=21#wechat_redirect*/
            System.out.println("after invoke");
            return obj;
        });
        Student stu = (Student) enhancer.create();
        stu.eat();

    }
}
