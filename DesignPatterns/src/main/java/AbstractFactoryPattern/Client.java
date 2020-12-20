package AbstractFactoryPattern;

import AbstractFactoryPattern.impl.FastFactory;
import AbstractFactoryPattern.impl.GoodFactory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 抽象工厂模式<br>
 * 在抽象产品的基础上将工厂也给抽象了，
 * 通过调用接口类的静态方法首先生产工厂（实例化工厂），然后由工厂生产相应的产品
 *
 * 实际上工厂方法只抽象了产品，而工厂是个具体的类。而抽象工厂模式连工厂也给抽象为了Interface!
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //第一种方式：
        AbstractFactory fastFactory = new FastFactory();
        HTMLDocument fastHtml= fastFactory.createHTMLDocument("fast html");
        WordDocument fastWord= fastFactory.createWordDocument("fast word");
        fastHtml.save(Paths.get(".", "..."));
        fastWord.save(Paths.get(".", "..."));  // 这个Paths类和Arrays有点像，在后面加了s的类目前发现后两个是工具类

        AbstractFactory goodFactory = new GoodFactory();
        HTMLDocument goodHtml = goodFactory.createHTMLDocument("good html");
        WordDocument goodWord= goodFactory.createWordDocument("goof word");
        // TODO: 2020/11/13 此处省略good产品的使用


        //第二种方式，更加清晰：
        FastFactory fastFactory1 = AbstractFactory.createFactory(FastFactory.class);
        GoodFactory goodFactory1 = AbstractFactory.createFactory(GoodFactory.class);
        // TODO: 2020/11/28 次数省略生产商品的过程

    }
}
