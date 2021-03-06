package PrototypePattern;

import java.math.BigDecimal;

/**
 * 问答题
 */
public class AnswerQuestion {
    private String name;  //问题
    private String key;  //答案

    public AnswerQuestion(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public AnswerQuestion() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
