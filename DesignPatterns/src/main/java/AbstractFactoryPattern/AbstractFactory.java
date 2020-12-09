package AbstractFactoryPattern;

import AbstractFactoryPattern.impl.FastFactory;
import AbstractFactoryPattern.impl.GoodFactory;

/**
 * 抽象工厂模式
 * 这种模式其实是对**工厂**的抽象（不然为什么会叫抽象工厂模式呢，是吧）
 * 本例子来自<link>https://www.liaoxuefeng.com/wiki/1252599548343744/1281319134822433</link>
 * 注意最后采用了一种更简洁的方式，即直接申明一个静态方法，这种方式更厉害！
 */
public interface AbstractFactory {

    HTMLDocument createHTMLDocument(String md);
    WordDocument createWordDocument(String md);

    //这种方式更加有效
    public static <T extends AbstractFactory> T createFactory(Class<T> clazz) {
        String name =  clazz.getName();
        if (name.equalsIgnoreCase("fast")){
            return (T) new FastFactory();
        }else if (name.equalsIgnoreCase("good")) {
            return (T) new GoodFactory();
        }else throw new IllegalArgumentException("invalid factory name");
    }
}
