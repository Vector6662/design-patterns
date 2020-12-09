package AbstractFactoryPattern.impl;

import AbstractFactoryPattern.AbstractFactory;
import AbstractFactoryPattern.HTMLDocument;
import AbstractFactoryPattern.WordDocument;

/**
 * 创建GoodDoc soft系列产品的工厂
 */
public class GoodFactory implements AbstractFactory {
    @Override
    public HTMLDocument createHTMLDocument(String md) {
        return new FastHtmlDocument();
    }

    @Override
    public WordDocument createWordDocument(String md) {
        return new FastWordDocument();
    }
}
