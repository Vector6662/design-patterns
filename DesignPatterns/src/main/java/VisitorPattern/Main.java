package VisitorPattern;

import java.io.File;
import java.util.logging.FileHandler;

/**
 * 访问者模式
 * 个人理解就是解耦，解耦的双方是数据和操作
 * 数据如果会被各种各样的业务所需要的话，就可以采用这种模式，
 * 也就是将 1.获取（产生）数据和 2.根据不同的业务场景处理数据 两个部分分离开
 */
public class Main {
    public static void main(String[] args) {
        FileStructure f = new FileStructure(new File(Main.class.getResource("/").getPath()));
        JavaFileVisitor visitor1 = new JavaFileVisitor();
        ClassFileVisitor visitor2 = new ClassFileVisitor();
        f.handle(visitor2);

    }
}
