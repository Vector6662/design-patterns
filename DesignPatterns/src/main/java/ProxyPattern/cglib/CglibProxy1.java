package ProxyPattern.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 方式一
 * 用cglib实现动态代理
 * 该方式的关键类其实是Enhancer，而不是MethodInterceptor
 * 这种方式实现起来更加清晰，其实就是分为三步走：1.获取Enhancer对象；2.设置enhancer对象的父类，也就是被代理对象；3.设置回调
 */
public class CglibProxy1 {
    public static void main(String[] arguments) {
        /*实际上这个Enhancer类应该写在一个代理类里也就是下面链接中采用的方式，并用泛型。比较符合开发规范*/
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Student.class);
        /*
         * 翻译一下setCallback()的参数：
         * 参数列表：
         * obj - "this"，被增强的对象，应该就是这个enhancer实例本身，也是目标对象的子类（不是很确定，但是能确定的是肯定不是Student实例）
         * method - 被拦截的方法
         * args - 参数数组，基本数据类型会被装箱
         * proxy - 用于调用父类（即未被拦截的方法 这里翻译应该有问题）；可以根据需要被调用很多次
         * */
        //注意这里有个细节，用lambda表达式时，setCallback()的参数接受的是Callback类型，虽然MethodInterceptor是其子类，但是这里还是要强转一下
        enhancer.setCallback((MethodInterceptor) (o, method, args, methodProxy) -> {
            System.out.println("before invoke");
            Object obj = methodProxy.invokeSuper(o,args);//为什么是invokeSuper()而不是直接invoke？这是因为这个enhancer类是作为我的目标类的子类的回调
            /*
            * 或者采用另一种方式也是可以的，参考https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483954&idx=1&sn=b34e385ed716edf6f58998ec329f9867&chksm=ebd74333dca0ca257a77c02ab458300ef982adff3cf37eb6d8d2f985f11df5cc07ef17f659d4&scene=21#wechat_redirect
            * 即method.invoke(target,args); 即采用和JDK的Proxy类一样的调用方式。注意这里的target参数指的是被代理对象（实例）
            * */
            System.out.println("after invoke");
            return obj;
        });
        Student stu = (Student) enhancer.create();
        stu.eat();

    }
}
