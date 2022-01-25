package Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    static void serialize() {
        try {
            Student student = new Student();
            student
                    .setName("sanbing")
                    .setAge(10)
                    .setScore(100);
            File f = new File("/");
            /*
            * File f = new File("");
            * f.getAbsolutePath();
            * 这里有个很有趣的现象，f.getAbsolutePath();得到的结果是C:\Users\liuhaodong\IdeaProjects\design-patterns\DesignPatterns
            * 只是项目根目录。
            * 还有一点，下面的代码中将student.txt放在了resources下，但是在其编译后的target目录中却没有resources目录，
            * resources里面的META-INF和student.txt等资源都在classes直接目录下
            * */
            File file = new File("src/main/resources/student.txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(student);
            outputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    static void deserialize(){
        try{
            File file = new File("src/main/resources/student.txt");
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            Student student = (Student) inputStream.readObject();
            inputStream.close();
            logger.info("student反序列化的结果为{}",student);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        serialize();
        deserialize();
    }
}
