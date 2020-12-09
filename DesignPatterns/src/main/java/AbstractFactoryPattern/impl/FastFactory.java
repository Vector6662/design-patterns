package AbstractFactoryPattern.impl;

import AbstractFactoryPattern.AbstractFactory;
import AbstractFactoryPattern.HTMLDocument;
import AbstractFactoryPattern.WordDocument;

/**
 * 生产FastDoc Soft系列产品
 */
public class FastFactory implements AbstractFactory {
    @Override
    public HTMLDocument createHTMLDocument(String md) {
        return new FastHtmlDocument();
    }

    @Override
    public WordDocument createWordDocument(String md) {
        return new FastWordDocument();
    }
}
