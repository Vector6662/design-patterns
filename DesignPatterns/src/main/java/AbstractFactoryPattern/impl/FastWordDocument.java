package AbstractFactoryPattern.impl;

import AbstractFactoryPattern.WordDocument;

import java.io.IOException;
import java.nio.file.Path;

/**
 * FastDoc soft系列产品
 */
public class FastWordDocument implements WordDocument {
    @Override
    public void save(Path path) throws IOException {
        logger.info("保存FastDoc soft系列产品-->{}",path);
    }
}
