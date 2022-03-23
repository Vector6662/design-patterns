package VisitorPattern;

import java.io.File;
import java.util.logging.FileHandler;

/**
 * 访问者模式
 * 个人理解就是解耦，解耦的双方是数据和操作
 * 数据如果会被各种各样的业务所需要的话，就可以采用这种模式，
 * 也就是将 1.获取（产生）数据和 2.根据不同的业务场景处理数据 两个部分分离开
 *
 * 最后采用了函数式编程的额思想，尝试了一下，很成功！
 */
public class Main {
    public static void main(String[] args) {
        File file = new File(Main.class.getResource("/").getPath());
        FileStructure fs = new FileStructure(file);
        JavaFileVisitor visitor1 = new JavaFileVisitor();
        ClassFileVisitor visitor2 = new ClassFileVisitor();
        fs.handle(visitor1);
        fs.handle(visitor2);

        //尝试使用函数式编程
        System.out.println("================函数式编程=================");
        fs.handle(file,(i)->{//相当于是实现Visitor接口，但又不全是，因为这里的Visitor接口有两个方法，functionalinterface只能是一个方法
            if (i.getName().endsWith(".class")){
                System.out.println("found java file:"+i);
            }
        });

    }
}
