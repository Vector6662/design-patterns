package AdaptorPattern;

import java.util.concurrent.Callable;

/**
 * 实现Runnable对Callable的适配器
 * 在 廖雪峰的 https://www.liaoxuefeng.com/wiki/1252599548343744/1281319245971489 中的最后小结处：
 * 编写Adapter实际上就是编写一个实现了B接口，并且内部持有A接口的类
 * 可以说是总结得很精辟了
 *
 * 也就是说，对象A作为参数传入方法或者构造器中时，有可能因为类型的不匹配导致编译失败，但这个时候又不想改变这个类本身，
 * 那么就采用一种折中的方式：重新写一个类（中间类）包装一下这个对象A，这个类一定是所需参数B的子类或者实现类（implement）
 */
public class RunnableAdaptor implements Runnable {
    private Callable<?> callable;  // 细节，学会使用通配符?，而不是空着。即使空着也不是啥错误，但是不符合规范啊！
    public RunnableAdaptor(Callable<?> callable) {
        this.callable = callable;
    }
    @Override
    public void run() {
        try {
            callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
