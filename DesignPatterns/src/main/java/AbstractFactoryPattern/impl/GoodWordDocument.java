package AbstractFactoryPattern.impl;

import AbstractFactoryPattern.WordDocument;

import java.io.IOException;
import java.nio.file.Path;

/**
 * GoodDoc Soft系列产品
 */
public class GoodWordDocument implements WordDocument {
    @Override
    public void save(Path path) throws IOException {
        logger.info("保存GoodDoc soft系列产品-->GoodHtml");
    }
}
