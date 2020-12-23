package HotSwap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

public class MyClassLoader extends ClassLoader {
    //用于读取.class文件的路径
    private String swapPath;
    //用于标记这些name的类是先由自身加载的
    private Set<String> useMyClassLoaderLoad;
    public MyClassLoader(String swapPath, Set<String> useMyClassLoaderLoad){
        this.swapPath=swapPath;
        this.useMyClassLoaderLoad=useMyClassLoaderLoad;
    }



    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        /*总的逻辑是，
        1.查找该class对象是否已经被加载；
        2.如果没有被加载，也不是我这个自定义的类加载器加载的，那么就通过调用重写的findClass()获取此类
        3.如果”2.“为假，则调用父类的loadClass()方法，即采用双亲委派模型来进行加载
        （不知道这样做是傻逼原因，既然该类已经被加载了，为啥还要调用该方法再记载一次？可能是findLoadedClass()有啥幺蛾子吧，过后再研究一下）*/
        Class<?> c = findLoadedClass(name);
//        System.out.println("c = " + c);刚刚测试过，在Main方法这样的调用情形下，c永远都是为null的，一定会执行if中的语句，调用自己重写的findClass()，从磁盘中加载一个类
        if (c==null && useMyClassLoaderLoad.contains(name)){
            //特殊的类让我自己加载，模板方法模式
            c=findClass(name);
            if (c!=null) return c;
        }
        return super.loadClass(name);

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
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
