package JPA;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 模拟实现JPA框架，这里还练习了泛型的使用以及对Class类及其泛型的理解
 */
public class Main {
    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        ActorDao actorDao = new ActorDao();
        Actor actor = new Actor(201, "Vector", "Liu",df.format(new Date()));
        actorDao.add(actor);
    }
}
