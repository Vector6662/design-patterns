package JPA;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 * 本例来源于https://zhuanlan.zhihu.com/p/60966151
 * 使用了反射和注解这两种高端玩法。
 * 但是在操作过程中还是发现其实可以直接使用泛型来获取bean对象的
 *
 * @param <T>
 */
public class BaseDao<T> {
    private static BasicDataSource dataSource = new BasicDataSource();
    static {
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    //泛型参数的Class对象，这个对象是关键，是需要通过反射解析的对象
    private Class<T> beanClass;

    public BaseDao(){
        /*this指代调用这个构造方法的对象，一般是子类对象，
        * 通过子类得到子类传给父类的泛型class对象，假设是Actor.class*/
        Class clazz = this.getClass();
        ParameterizedType type= (ParameterizedType) clazz.getGenericSuperclass();
        beanClass = (Class)type.getActualTypeArguments()[0];

    }

    public void add(T bean) {
        //得到bean对象的所有字段
        /*
        * 我才用的方式救护需要在构造方法中获得的beanClass对象了
        * 参考文章中采用的方式是：
        * Field[] declaredFields = beanClass.getDeclaredFields();
        * 与我这种方式的区别在哪里呢？
        * 我的理解是Filed[]对象的获取途径的不同，我的方式是直接通过类型实参得到的，而文中是通过父类泛型类
         * */
        Field[] fields= bean.getClass().getDeclaredFields();
        //拼接sql语句，表名从注解中获取
        StringBuilder sql = new StringBuilder("insert into "
                + bean.getClass().getAnnotation(Table.class).value()
                + " values(");
        for (int i=0; i<fields.length; i++) {
            sql.append("?");
            if (i < fields.length - 1) sql.append(",");
        }
        sql.append(")");

        System.out.println(sql.toString());

        // 获得bean字段的值（要插入的记录）
        ArrayList<Object> paramsList = new ArrayList<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object o = field.get(bean);
                paramsList.add(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        int size = paramsList.size();
        Object[] params = paramsList.toArray();
        System.out.println(paramsList);

        int num = jdbcTemplate.update(sql.toString(), params);
        System.out.println(num);

    }


}
