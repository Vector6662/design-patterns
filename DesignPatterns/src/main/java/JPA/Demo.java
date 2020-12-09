package JPA;

/**
 * 这是一个基础demo，用来理解this关键字
 * 这是一个非常重要的知识点，灵感来源于：https://zhuanlan.zhihu.com/p/111804664
 * 里面提到this可以与python中方法的第一个参数this相类比，这个思路是非常好的
 * 同时将构造器（构造方法）理解为特殊的**方法**最好，调用一个实例方法或者构造器，其实都会隐式传递调用者实例对象：this
 */
public class Demo {
    public static void main(String[] args) {
        new B().func();
    }

}

class A <T>{
    public A(){
        /*这里有一个我以前从来没意识到的知识点，这里的this其实是一个连续传递的对象参数，和python的self很像*/
        Class clazz = this.getClass();
        System.out.println(clazz);
    }
    void func(){
        System.out.println(this.getClass().getName());
    }
}

/*从这个例子中我发现一个惊天的秘密：默认构造器其实是无参构造器！！！*/
class B extends A<String>{

    public B() {
        super();  // 这里隐式传递了参数this，把构造器理解为一种方法就更好理解了
        // 本质和子类调用方法给父类传参一样一样的
    }
}