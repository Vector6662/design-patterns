package VisitorPattern;

import java.io.File;

public class ClassFileVisitor implements Visitor {
    @Override
    public void visitDir(File dir) {
        System.out.println("Visit dir:"+dir);
    }

    @Override
    public void visitFile(File file) {
        if (file.getName().endsWith(".class")){
            System.out.println("will clean class file:"+file);
        }

    }
}
