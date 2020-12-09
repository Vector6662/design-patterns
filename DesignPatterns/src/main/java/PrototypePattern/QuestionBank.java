package PrototypePattern;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 实现原型模式的关键类
 */
public class QuestionBank implements Cloneable {
    private String candidate;  //考生
    private String number;


    private ArrayList<ChoiceQuestion> choiceQuestions = new ArrayList<ChoiceQuestion>();  //有考虑过采用List，多态好些，但是List没有实现Cloneable
    private ArrayList<AnswerQuestion> answerQuestions = new ArrayList<AnswerQuestion>();

    public QuestionBank append(ChoiceQuestion choiceQuestion) {
        this.choiceQuestions.add(choiceQuestion);
        return this;
    }
    public QuestionBank append(AnswerQuestion answerQuestion) {
        this.answerQuestions.add(answerQuestion);
        return this;
    }

    @Override
    public String toString() {
        return "QuestionBank{" +
                "candidate='" + candidate + '\'' +
                ", number='" + number + '\'' +
                ", choiceQuestions=" + choiceQuestions +
                ", answerQuestions=" + answerQuestions +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        /*注意一下操作都是对新的QuestionBank对象进行的，这个新的对象是被克隆出来的（也就是下面的第一段代码）*/
        /*为什么后面需要手动添加choiceQuestions和answerQuestions属性呢？调用的是super.clone()，这里的
        super是Object对象，这个对象当然是没有这两个属性的*/
        QuestionBank questionBank = (QuestionBank) super.clone();
        System.out.println(questionBank);
        questionBank.choiceQuestions = (ArrayList<ChoiceQuestion>) this.choiceQuestions.clone();
        questionBank.answerQuestions = (ArrayList<AnswerQuestion>) this.answerQuestions.clone();

        /*上面已经克隆了一个原对象的镜像，现在才开始进行我的业务逻辑：*/

        /*题目乱序*/
        Collections.shuffle(questionBank.choiceQuestions);
        Collections.shuffle(questionBank.answerQuestions);

        // TODO: 2020/11/4 选择题答案乱序


        return super.clone();
    }


}