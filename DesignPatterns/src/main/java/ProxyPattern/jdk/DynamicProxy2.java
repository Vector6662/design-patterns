package ProxyPattern.jdk;

import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class DynamicProxy2 {

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();  //申明被代理类，我觉得我这个习惯很好，变量类型是接口，但是对象是它的实现类，利用了多态。请保持这个习惯！
        Calculator calculatorProxy = getProxy(calculator);  //得到代理对象
        Double ans =calculatorProxy.add(1.3,2.3);
        System.out.println(ans);

    }


    /**
     * 这是一个非常值得把玩的例子，相当于实现了**通用**的代理，非常先进啊！
     * @param target
     * @return
     */

    // TODO: 2020/11/2 尝试使用泛型，避免强制转换。
    //  已实现，如下，但这似乎还可以优化，因为newProxyInstance()并不支持泛型，它返回的只是Object对象。
    //  但这又是很好的方式了，因为泛型只是一个语法糖，只是在编译期间有效，编译完后泛型擦除，所有泛型类型都还是会转换为Object类型
    /*但是这么做感觉对泛型的认识更深刻了些，你看这个泛型形参T申明了一下之后它和变量名target一样被赋值了！这是非常神奇的地方！
    * 以前怎么没意识到？？？！！！
    * 这种现象的正名叫做类型推到，记住这个名字*/
    static <T> T getProxy(final T target) {

        /*
        * 为了使得逻辑清晰，loader参数和interfaces参数都用target的
        * 请注意，这个方法（newProxyInstance）返回的是代理对象，而不是target自己！！！这两者是不一样的哦！！！
        * */
        return (T) Proxy.newProxyInstance(
                //我觉得这里选择三个基本的ClassLoader都是可以的，因为遵循双亲委派模型。但是如果是自定义的ClassLoader并且重写了loaderClass()，那就是另一会事了
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("实现代理");
                    System.out.println("模拟AOP 前置");
                    Object obj = method.invoke(target,args);
                    System.out.println("模拟AOP 后置");
                    return obj;
                });
    }

}

