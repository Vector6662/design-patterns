package ProxyPattern.jdk;



import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.lang.reflect.*;
import java.util.Arrays;


public class DynamicProxy1 {
    public static void main(String[] args) throws Exception {
        final Logger logger = LoggerFactory.getLogger(DynamicProxy1.class);
        Calculator target = new CalculatorImpl();


        /*
        * 是不是真的第一个参数任何一个Class对象都可以？分别尝试DynamicProxy的Class对象和Calculator的Class对象，结果很意外，
        * 居然随便定义的类Test都可以，看来醉翁之意不在酒啊，重点还是后面的interfaces可变长参数
        * 第二个参数细节请注意，接受的是可变长参数列表，可以是多个Class类型的对象（A.class, B.class），
        * 也可以是一个new Class[]{A.class, B.class}
        *
        * 其实一个比较好的做法是这两个参数就是被代理对象本身的Loader和Interfaces，
        * 即：(target.getClass().getClassLoader, target.getInterfaces())，这样的思路显得清晰些
        *
        * 本例是一个比较“原始”的做法，一般都是直接调用newProxyInstance()
        */
        Class<?> calculatorProxyClazz = Proxy.getProxyClass(Test.class.getClassLoader(), Calculator.class);

        System.out.println("---目标类的父类---");
        System.out.println(calculatorProxyClazz.getSuperclass());
        System.out.println("---目标类实现的接口们---");
        System.out.println(Arrays.toString(calculatorProxyClazz.getInterfaces()));
        System.out.println("---代理类构造器列表---");
        printClassInfo(calculatorProxyClazz.getConstructors());
//        System.out.println("---代理类方法列表---");
//        printClassInfo(calculatorProxyClazz.getMethods());

        /*
        * 为什么这里要传入InvocationHandler.class？
        * 这个问题非常有意思！因为通过这种方式（Proxy.getProxyClass(...)）得到的代理类会有且仅有一个构造器：
        * $Proxy0(java.lang.reflect.InvocationHandler)
        * 这里有点意思哦，这个构造器是Proxy的构造器，而不是我的目标Calculator的哦。我上边打印的目标类的父类正好也是Proxy，哈！这就很清楚了：
        * 我的目标是一个接口，但这是没有构造器的，那么就用Proxy实现这个接口，不就有构造器了吗！（描述不是很清晰，过后优化一下表述）
        *
        * 这个构造器只有一个形参InvocationHandler。所以调用getConstructor()传入的参数就只能是InvocationHandler.class，
        * 没得选，而且必须填，因为它甚至没有空构造器...
        *
        * 区别getDeclaredConstructor()，这个构造器会返回包括非public的构造器，而getConstructor()只会返回public的构造器
        * */
        Constructor<?> constructor = calculatorProxyClazz.getConstructor(InvocationHandler.class);
        /*
        * InvocationHandler是干嘛的呢？从实验结果看，会发现每次调用代理对象的方法，最终都会调用InvocationHandler的invoke()方法
        * */
        Calculator calculatorProxy = (Calculator) constructor.newInstance(
                (InvocationHandler) (proxy, method, args1) -> {
            System.out.println("before-调用了代理，这里应该写实现代理的内容");

            /*这个返回值也不是乱来的：
             * Return - the value to return from the method invocation on the proxy instance.
             * 从代理实例的方法调用返回的值
             * */
            Object obj =  method.invoke(target, args1);

            System.out.println("after-调用了代理，这里应该写实现代理的内容");

            return obj;
        });

        if(calculatorProxy instanceof Proxy) System.out.println("true");

        System.out.println("----调用代理对象----");
        Double d = calculatorProxy.multiple(1.20,2.01);
        System.out.println(d);

    }


    public static void printClassInfo(Executable[] targets) {
        for (Executable target : targets) {
            // 构造器/方法名称
            String name = target.getName();
            StringBuilder sBuilder = new StringBuilder(name);
            // 拼接左括号
            sBuilder.append('(');
            Class<?>[] clazzParams = target.getParameterTypes();
            // 拼接参数
            for (Class<?> clazzParam : clazzParams) {
                sBuilder.append(clazzParam.getName()).append(',');
            }
            //删除最后一个参数的逗号
            if (clazzParams.length != 0) {
                sBuilder.deleteCharAt(sBuilder.length() - 1);
            }
            //拼接右括号
            sBuilder.append(')');
            //打印 构造器/方法
            System.out.println(sBuilder.toString());
        }
    }

}