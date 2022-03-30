package 核弹试验场;

import BlockingQueue.ArrayBlockingQueue;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        // 新建Person对象，
        Person p1 = new Person("eee", 100);
        Person p2 = new Person("eee", 100);
        Person p3 = new Person("aaa", 200);

        // 新建HashSet对象
        HashSet<Person> set = new HashSet<>();
        set.add(p1);
        set.add(p2);
        set.add(p3);

        // 比较p1 和 p2， 并打印它们的hashCode()
        System.out.printf("p1.equals(p2) : %s; p1(%d) p2(%d)\n", p1.equals(p2), p1.hashCode(), p2.hashCode());
        // 打印set
        System.out.printf("set:%s\n", set);
    }

    private static class Person {

        private String name;

        private int age;

        public Person(String name, int age) {
            super();
            this.name = name;
            this.age = age;
        }

        /**
         * 重写toString方法
         */
        @Override
        public String toString() {
            return "("+name + ", " +age+")";
        }

        /**
         * 重写equals方法
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            // 如果是同一个对象返回true，反之返回false
            if (this == obj) {
                return true;
            }

            // 判断是否类型相同
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            Person person = (Person) obj;
            return name.equals(person.name) && age == person.age;
        }
    }
}
