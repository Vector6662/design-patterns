package ProxyPattern;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

public class InvocationHandlerTest {

    public static void main(String[] args) throws Exception {
        CalculatorImpl calculator = new CalculatorImpl();
        Calculator proxy = (Calculator) MyProxy.newProxyInstance(
                calculator.getClass().getClassLoader(),
                calculator.getClass().getInterfaces(),
                new MyInvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("前置操作...");
                        Object result = method.invoke(calculator, args);
                        System.out.println("后置操作");
                        return result;
                    }
                });
        proxy.add(1, 2);
    }

    /**
     * 山寨Proxy类
     */
    public static class MyProxy implements java.io.Serializable {

        protected MyInvocationHandler h;

        private MyProxy() {
        }

        protected MyProxy(MyInvocationHandler h) {
            Objects.requireNonNull(h);
            this.h = h;
        }

        public static Object newProxyInstance(ClassLoader loader,
                                              Class<?>[] interfaces,
                                              MyInvocationHandler h) throws Exception {
            // 拷贝一份接口Class（接口可能有多个，所以拷贝的Class也有多个）
            final Class<?>[] interfaceCls = interfaces.clone();
            // 这里简化处理，只取第一个
            Class<?> copyClazzOfInterface = interfaceCls[0];
            // 获取Proxy带InvocationHandler参数的那个有参构造器
            Constructor<?> constructor = copyClazzOfInterface.getConstructor(MyInvocationHandler.class);
            // 创建一个Proxy代理对象，并把InvocationHandler塞到代理对象内部，返回代理对象
            return constructor.newInstance(h);
        }

    }

    /**
     * 山寨InvocationHandler接口
     */
    interface MyInvocationHandler {
        Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
    }

    /**
     * 需要代理的接口
     */
    interface Calculator {
        int add(int a, int b);
    }

    /**
     * 目标类
     */
    static class CalculatorImpl implements Calculator {
        @Override
        public int add(int a, int b) {
            return a + b;
        }
    }


}