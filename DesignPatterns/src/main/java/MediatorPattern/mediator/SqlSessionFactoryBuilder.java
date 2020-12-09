package MediatorPattern.mediator;

import java.io.Reader;

/**
 * 这是最为关键的类，主要的目的是读取有关mybaties和数据库的配置文件，
 * 最终的目的是创建 SqlSessionFactory。这个过程比较繁琐，因为要读取xml配置文件，
 * 所以这里采用了建造者模式，且只有一个公共方法build()
 */
public class SqlSessionFactoryBuilder {
    public DefaultSqlSessionFactory build(Reader reader) {
        return null;
    }
}
