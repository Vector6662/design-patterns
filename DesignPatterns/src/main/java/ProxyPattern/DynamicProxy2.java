package ProxyPattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class DynamicProxy2 {

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();  //申明被代理类，我觉得我这个习惯很好，变量类型是接口，但是对象是它的实现类，利用了多态。请保持这个习惯！
        Calculator calculatorProxy = getProxy(calculator);  //得到代理对象
        calculatorProxy.add(1.3,2.3);
        calculatorProxy.add(1.32,3434.2);

    }


    /**
     * 这是一个非常值得把玩的例子，相当于实现了**通用**的代理，非常先进啊！
     * @param target
     * @return
     */

    // TODO: 2020/11/2 尝试使用泛型，避免强制转换。
    //  已实现，如下，但这似乎不是特别好的方式，因为newProxyInstance()可不支持泛型，它返回的只是Object对象
    /*但是这么做感觉对泛型的认识更深刻了些，你看这个泛型形参T申明了一下之后它和变量名target一样被赋值了！这是非常神奇的地方！
    * 以前怎么没意识到？？？！！！*/
    static <T> T getProxy(final T target) {
        Object.class.getClassLoader();


        /*
        * 为了使得逻辑清晰，loader参数和interfaces参数都用target的
        * */
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("实现代理");
                    System.out.println("模拟AOP");
                    return method.invoke(target, args);
                });
    }

}

