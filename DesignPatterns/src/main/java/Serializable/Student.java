package Serializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Student implements Serializable {

//    private static final long serialVersionUID = -1576643344804979563L;

    private String name;
    private Integer age;
    private Integer score;
    private Long studentId;

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Student setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public Student setScore(Integer score) {
        this.score = score;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

    private void readObject( ObjectInputStream objectInputStream ) throws IOException, ClassNotFoundException {

        // 调用默认的反序列化函数
        objectInputStream.defaultReadObject();

        // 手工检查反序列化后学生成绩的有效性，若发现有问题，即终止操作！
        if( 0 > score || 100 < score ) {
            throw new IllegalArgumentException("学生分数只能在0到100之间！");
        }
    }
}
