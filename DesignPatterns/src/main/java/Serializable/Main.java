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
