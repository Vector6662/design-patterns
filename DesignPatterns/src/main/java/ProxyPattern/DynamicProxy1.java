package ProxyPattern;



import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.lang.reflect.*;
import java.util.Arrays;


public class DynamicProxy1 {
    public static void main(String[] args) throws Exception {
        final Logger logger = LoggerFactory.getLogger(DynamicProxy1.class);


        /*
        * 是不是真的第一个参数任何一个Class对象都可以？分别尝试DynamicProxy的Class对象和Calculator的Class对象，结果很例外，
        * 居然随便定义的类Test都可以，看来醉翁之意不在酒啊，重点还是后面的interfaces可变长参数
        * 第二个参数细节请注意，接受的是可变长参数列表，可以是多个Class类型的对象（A.class, B.class），
        * 也可以是一个new Class[]{A.class, B.class}
        *
        * 其实一个比较好的做法是这两个参数就是被代理对象本身的Loader和Interfaces，即：(target.getClass().getClassLoader, target.getInterfaces())，这样的思路清晰些
        *
        * 本例是一个比较“原始”的做法，一般都是直接调用newProxyInstance()
        */
        Class<?> calculatorProxyClazz = Proxy.getProxyClass(Test.class.getClassLoader(), Calculator.class);
        System.out.println(Arrays.toString(calculatorProxyClazz.getMethods()));
        Constructor<?> constructor = calculatorProxyClazz.getConstructor(InvocationHandler.class);
        Calculator calculatorProxy = (Calculator) constructor.newInstance(new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("调用了代理，这里应该写实现代理的内容");

                /*这个返回值也不是乱来的：
                 * Return - the value to return from the method invocation on the proxy instance. 从代理实例的方法调用返回的值
                 *
                 * */
                return method.invoke(new CalculatorImpl(), args);

            }
        });

        Double d = calculatorProxy.multiple(1.20,2.01);
        System.out.println(d);

    }

}


class Test{

}