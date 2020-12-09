package AbstractFactoryPattern.impl;

import AbstractFactoryPattern.HTMLDocument;

import java.io.IOException;
import java.nio.file.Path;

/**
 * GoodDoc Soft系列产品
 */
public class GoodHtmlDocument implements HTMLDocument {
    @Override
    public String toHtml() {
        return "得到GoodDoc soft系列产品-->GoodHtml";
    }

    @Override
    public void save(Path path) throws IOException {
        logger.info("保存GoodDoc soft系列产品-->GoodHtml");

    }
}
