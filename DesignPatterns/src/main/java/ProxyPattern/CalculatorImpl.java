package ProxyPattern;

/**
 * 实现计算器接口，但是此时需要实现代理，因为需要多参数a和b进行判断是否合法等等，比如b为0则不能进行出发运算
 * 采用的方式是动态代理
 */
class CalculatorImpl implements Calculator {

    public Double add(Double a, Double b) {
        return a + b;
    }

    public Double subtract(Double a, Double b) {
        return a / b;
    }

    public Double multiple(Double a, Double b) {
        return a * b;
    }
}
