package AbstractFactoryPattern.impl;

import AbstractFactoryPattern.HTMLDocument;

import java.io.IOException;
import java.nio.file.Path;

/**
 * FastDoc soft系列产品
 */
public class FastHtmlDocument implements HTMLDocument {

    @Override
    public String toHtml() {
        return "得到FastDoc soft系列产品-->";
    }

    @Override
    public void save(Path path) throws IOException {
        logger.info("保存FastDoc soft系列产品-->FastHtml");
    }
}
