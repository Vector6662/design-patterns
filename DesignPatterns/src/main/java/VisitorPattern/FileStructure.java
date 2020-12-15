package VisitorPattern;

import java.io.File;

/**
 * 一个能持有文件夹和文件的数据结构
 */
public class FileStructure {
    private File path;
    public FileStructure(File path){
        System.out.println("初始化目录："+path.getAbsolutePath());
        this.path=path;
    }

    public void handle(Visitor visitor){
        scan(this.path,visitor);
    }

    /**
     * 这里的业务逻辑也很简单：拿到visitor想要的数据（file）后交给visitor
     * @param file
     * @param visitor
     */
    private void scan(File file,Visitor visitor){
        if (file.isDirectory()){
            visitor.visitDir(file);
            for (File subFile:file.listFiles()){
                scan(subFile,visitor);
            }
        } else if (file.isFile()) {
            visitor.visitFile(file);
        }
    }

}
