package AbstractFactoryPattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

/**
 * word文档接口
 */
public interface WordDocument {
    Logger logger = LoggerFactory.getLogger(WordDocument.class); //似乎用类.class方式比this.getClass()方式代码健壮些，”提高了代码的鲁棒性“
    void save(Path path) throws IOException;
}
