package PubSubPattern;

import java.util.Date;

public class Result {
    public Result(String uId, Date date, String info) {
        this.uId = uId;
        this.date = date;
        Info = info;
    }

    @Override
    public String toString() {
        return "Result{" +
                "uId='" + uId + '\'' +
                ", date=" + date +
                ", Info='" + Info + '\'' +
                '}';
    }

    private String uId;
    private Date date;
    private String Info;
}
