package IteratorPattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 迭代器模式
 */
public class Main {
    public static void main(String[] args) {
        /**
         * 这个例子是廖雪峰老师提供的：https://www.liaoxuefeng.com/wiki/1252599548343744/1281319524892705
         * 这个例子相当于复现了常用的ArrayList遍历底层是如何实现的，还有解释了增强for循环的本质：
         * 实际上，因为Iterator模式十分有用，因此，Java允许我们直接把任何支持Iterator的集合对象用foreach循环写出来，
         * 然后由Java编译器完成Iterator模式的所有循环代码。（这句话最重要！！！）
         *
         * 我对实现迭代器模式步骤的理解是：1.可遍历的目标类实现iterator()方法；2.需要创建一个Iterator的子类（这是关键！）
         * 其中关键方法是next()，每次调用能够返回目标元素，及泛型T
         */
        ReverseArrayCollection<Integer> r = new ReverseArrayCollection<Integer>(0,1,2,3,4,5,6,7,8,9);
        Iterator<Integer> i = r.iterator();
        //这里也是增强for循环的底层实现
        while (i.hasNext()) {
            System.out.println(i.next());
        }

        String s = "               ";
        int length=5;
        length=s.length()-length;
        System.out.println(length);
        s = s.replaceAll(" ","%20");
        s = s.substring(0,length);
        System.out.println(s);
    }



}
