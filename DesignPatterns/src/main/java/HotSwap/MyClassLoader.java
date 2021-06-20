package HotSwap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

/**
 * 破坏双亲委派模型并且自定义实现自己的类加载策略
 */
public class MyClassLoader extends ClassLoader {
    //用于读取.class文件的路径
    private String swapPath;
    //用于标记这些name的类是由MyClassLoader自己加载的
    private Set<String> useMyClassLoaderLoad;

    /**
     * 传入swap路径和需要特殊加载（热加载）的类名
     * @param swapPath
     * @param useMyClassLoaderLoad 需要特殊加载的类的binary name
     */
    public MyClassLoader(String swapPath, Set<String> useMyClassLoaderLoad){
        this.swapPath=swapPath;
        this.useMyClassLoaderLoad=useMyClassLoaderLoad;
    }

    /**
     * 破坏双亲委派模型，或者说自定义双亲委派模型。大致思路为：
     * 1.调用findLoadedClass()，目的是查找该class对象是否已经被加载，不论是父类加载还是自己；
     * 2.如果没有被加载，则需要加载此类，有两种情况：
     * （一）这个类属于需要特殊加载的类的集合，需要通过调用重写的findClass()获取此类；
     * （二）是需要特殊加载类，则调用父类的loadClass()方法，采用双亲委派模型来进行加载。
     *
     * 看到这里，我发现其实也不算破坏了双亲委派模型只是在原有双亲委派模型之前加了个自己的加载方式而已。
     * */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        /*
        * System.out.println("c = " + c);刚刚测试过，Main方法在当前这样的调用情形下，
        * c永远都是为null的，一定会执行if中的语句，调用自己重写的findClass()，从磁盘中加载一个类。
        *
        * 下面这个if判断的两个条件都很重要，其中第二个是判断name参数所代表的类是否需要热加载
        * */
        if (c==null && useMyClassLoaderLoad.contains(name)){
            //特殊的类用自定义的方法加载
            c=findClass(name);
            if (c!=null) return c;
        }
        // 程序如果能走到这里，那么表明参数name代表的类并不是需要特殊加载的类，则调用父类的加载方法，不破坏双亲委派模型
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) {
        /*
        * 父类无法加载，自己加载该类的方式
        * */
        //根据文件系统路径加载class文件，并返回byte数组
        byte[] classByte=getClassByte(name);
        //调用ClassLoader提供的方法，将二进制数组转换成Class类的实例
        return defineClass(name,classByte,0,classByte.length);
    }

    private byte[] getClassByte(String name){
        String className = name.substring(name.lastIndexOf('.')+1)+".class";
        try {
            FileInputStream fileInputStream = new FileInputStream(swapPath+className);
            byte[] buffer = new byte[1024];
            int length=0;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//这里好像还用不到装饰器模式，没有接受fileInputStream的构造器
            while ((length=fileInputStream.read(buffer))>0)
                byteArrayOutputStream.write(buffer,0,length);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e){
            e.printStackTrace();
        }
        return new byte[]{};
    }
}
