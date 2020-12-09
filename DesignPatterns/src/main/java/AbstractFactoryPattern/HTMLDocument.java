package AbstractFactoryPattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

/**
 * HTML文档接口
 */
public interface HTMLDocument {
    Logger logger = LoggerFactory.getLogger(HTMLDocument.class);
    String toHtml();
    void save(Path path) throws IOException;
}
