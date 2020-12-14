package IteratorPattern;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 廖雪峰博客提供的例子，相较于小博哥的例子比较基础
 * @param <T>
 */
public class ReverseArrayCollection<T> implements Iterable<T> {//强调一下基础知识，Iterable<T>上的T不是在声明泛型，而是在调用。声明过程是在ReverseArrayCollection<T>这里
    private T[] array;
    public ReverseArrayCollection(T... objs){
        this.array= Arrays.copyOfRange(objs,0,objs.length);
    }
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    //这里的T是ReverseArrayCollection这个最上层的类声明的泛型，所以这个内部类Itr是不需要再声明泛型T
    class Itr implements Iterator<T>{
        private int index;
        public Itr(){
            this.index=ReverseArrayCollection.this.array.length;
        }

        @Override
        public boolean hasNext() {
            return index>0;
        }

        @Override
        public T next() {
            index--;
            return array[index];
        }
    }
}
