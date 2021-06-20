package HotSwap;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 尝试实现代码热替换，在不重启服务器的情况下可以修改类的代码并使之生效。
 * 来源：https://zhuanlan.zhihu.com/p/54693308
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public void printVersion(){
       logger.info("这是版本1哦！");
    }

    public static void main(String[] args) {
        //创建一个2s执行一次的定时任务
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String swapPath = MyClassLoader.class.getResource("").getPath() + "/";
                String className = "HotSwap.Main";

                //每次都实例化一个ClassLoader，这里传入swap路径，和需要特殊加载的类名
                /*补充一下Sets这个工具类，看了下源码其实Sets#newHashSet方法的参数就是该方法返回的Set对象里面的元素*/
                MyClassLoader myClassLoader = new MyClassLoader(swapPath, Sets.newHashSet(className));
                try {
                    // 使用自定义的ClassLoader加载类，并调用printVersion方法。注意一定得显式调用myClassLoader加载，
                    // 否则会调用默认的AppClassLoader加载并实例化类。
                    // BTW，整个项目我用的是jdk1.8，（因此）直接用类来调用newInstance()这种方式没有被抛弃
                    Object o = myClassLoader.loadClass(className).newInstance();
                    // 下面两种写法应该都可以。但后面那一种有点多此一举，有两次类加载
                    o.getClass().getMethod("printVersion").invoke(o);
                    //myClassLoader.loadClass(className).getMethod("printVersion").invoke(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0,2000);
    }
}
