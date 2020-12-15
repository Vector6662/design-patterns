package VisitorPattern;

import java.io.File;

/**
 * 定义一个接口，表明访问者能够干的事情，用接口方法来体现
 */
public interface Visitor {
    void visitDir(File dir);
    void visitFile(File file);
}
