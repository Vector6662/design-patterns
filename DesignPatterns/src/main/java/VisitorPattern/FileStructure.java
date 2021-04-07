package VisitorPattern;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 一个能持有文件夹和文件的数据结构
 */
public class FileStructure {
    private File path;
    public FileStructure(File path){
        System.out.println("初始化目录："+path.getAbsolutePath());
        this.path=path;
    }

    /**
     * 数据只提供一个方法作为接口，供访问者访问
     * @param visitor
     */
    public void handle(Visitor visitor){
        scan(this.path,visitor);
    }

    public void handle(File file, Consumer<File> consumer){
        if(file.isDirectory()) {
            consumer.accept(file);
            for (File subFile:file.listFiles()){
                handle(subFile, consumer);
            }
        } else if (file.isFile()) {
            consumer.accept(file);
        }
    }

    /**
     * 这里的业务逻辑也很简单：拿到visitor想要的数据（file）后交给visitor
     * @param file
     * @param visitor
     */
    private void scan(File file,Visitor visitor){
        if (file.isDirectory()){
            /*
             * 我特么突然发现，这不就是传说中的回调吗！！！！！！！！！！
             * 在没有回调的方法里面，方法的参数都是作为**数据**使用，数据的意思是这个参数对象自己是不会调用方法的，
             * 比如这里的file参数就是，作为参数又传递给了visitDir()方法。
             * 而回调就很巧妙了，参数不是作为数据使用，而是“反客为主”：这个参数自己会调用方法，使用别的参数，就像
             * 这里的visitor对象一样！
             * 为什么叫做回调呢，而不叫阿猫阿狗呢？用我的话来讲就是回过头来再调用的意思（感觉当没解释...）
             * 回调的类visitor一般都是在方法体内业务代码写完后，最后再调用的。哦，这样清楚些了，回调指的是业务代码执行完毕
             * 后再调用。
             * 这样的好处也是解耦：业务代码是固定的，但是访问这段代码的对象是千变万化的，用回调就可以保有业务原有的流程，
             * 但访问者可以根据身情况完成自身的业务。
             * 参考：
             * Java 中回调机制是什么原理？ - RednaxelaFX的回答 - 知乎 https://www.zhihu.com/question/25504849/answer/130346710
             * 摘录其中的一段话：
             * 很多学习Java的同学喜欢说设计模式。嗯。那宽泛地说，其实什么strategy、template、observer、visitor模式
             * 全部都是回调的不同应用。简单来说就是本来可以写死在一起的代码给拆开来，把让其中一坨保持原有的流程，
             * 并在流程中挖出一些空，让另一坨代码作为参数传进来在流程中合适的地方被调用。
             */
            visitor.visitDir(file);
            for (File subFile:file.listFiles()){
                scan(subFile,visitor);//这里的逻辑也比较简单，获取到数据后调用访问者，这就达到了解耦的目的，很巧妙！
            }
        } else if (file.isFile()) {
            visitor.visitFile(file);
        }
    }

}
