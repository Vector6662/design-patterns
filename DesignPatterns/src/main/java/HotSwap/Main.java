package HotSwap;

import com.google.common.collect.Sets;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 尝试实现代码热替换，在不重启服务器的情况下可以修改类的代码并使之生效。
 * 来源：https://zhuanlan.zhihu.com/p/54693308
 */
public class Main {
    public void printVersion(){
        System.out.println("这是版本1哦");
    }

    public static void main(String[] args) {
        //创建一个2s执行一次的定时任务
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String swapPath = MyClassLoader.class.getResource("").getPath() + "/";
                String className = "HotSwap.Main";

                //每次都实例化一个ClassLoader，这里传入swap路径，和需要特殊加载的类名

                /*卧槽我发现这里才是关键啊！！！每次都实例化一个新的ClassLoader才是最核心的部分！！！
                * 这样的话loadClass中的findLoadedClass()方法就只会返回null，因为这个是新的ClassLoader，
                * 肯定不会有已加载的类！！！于是一定会执行其中的if代码块，然后调用重写的findClass()，从磁盘中加载Class字节码文件。*/
                MyClassLoader myClassLoader = new MyClassLoader(swapPath, Sets.newHashSet(className));
                try {
                    //使用自定义的ClassLoader加载类，并调用printVersion方法。
                    Object o = myClassLoader.loadClass(className).newInstance();
                    o.getClass().getMethod("printVersion").invoke(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0,2000);
    }
}
