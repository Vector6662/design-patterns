package VisitorPattern;

import java.io.File;

/**
 * 一个具体的访问者，两个方法中写自己的业务逻辑即可
 */
public class JavaFileVisitor implements Visitor {
    @Override
    public void visitDir(File dir) {
        System.out.println("Visit dir:"+dir);
    }

    @Override
    public void visitFile(File file) {
        if (file.getName().endsWith(".java")){
            System.out.println("found java file:"+file);
        }

    }
}
