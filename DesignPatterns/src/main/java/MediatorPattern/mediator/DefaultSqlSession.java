package MediatorPattern.mediator;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DefaultSqlSession implements SqlSession {
    private Connection connection;
    private Map<String, XNode> mapperElement;

    public DefaultSqlSession(Connection connection, Map<String, XNode> mapperElement) {
        this.connection = connection;
        this.mapperElement = mapperElement;
    }

    @Override
    public <T> T selectOne(String statement) {
        // TODO: 2020/11/28 验证这么些科学否，可以直接这么强制转换类型否？可以最泛型做出限制，<T extends Xxx>？
        return (T) selectList(statement).get(0);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return null;
    }

    @Override
    public <T> List<T> selectList(String statement) {
        /*实现具体的业务逻辑，原生的jdbc调用只在这里集中出现*/
        try {
            XNode xNode = mapperElement.get(statement);
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statement, Object parameter) {
        return null;
    }

    /**
     * ORM底层实现，将ResultSet对象封装到相应的实体类（在po目录下）中
     * @param resultSet sql查询结果
     * @param clazz 目标类，为啥Class<?>这里是 ? 而不是 T 啊，感觉这里是 T 更科学些
     * @param <T>
     * @return
     */
    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();  // 解析ResultSets对象所包含列的数字、类型以及属性等信息
            int columnCount = metaData.getColumnCount();  // 返回有多少个列
            // 每次遍历行（或说每条记录）的值
            while (resultSet.next()) {
                T obj = (T) clazz.newInstance();  // 首先获取目标实体类的实例
                // 依次将每个字段通过目标实体对象的setter方法赋值给实体对象的相应字段，于是数据库中的字段名（column name）要和对应的实体相同，否则找不到对应的setter方法
                for (int i = 1; i<=columnCount; i++) {
                    Object value = resultSet.getObject(i);  // setter方法参数所需的值
                    String columnName = metaData.getColumnName(i);  // 定位对应的setter方法
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);  //构造对应的setter方法名
                    Method method;
                    if (value instanceof Timestamp) {
                        method = clazz.getMethod(setMethod, Date.class);  //这一步应该是我的实体类中的时间字段不是TimeStamp类型而是Date类型，所以需要转换一下。也就是sql中的timestamp类型和date类型在Java中统一映射到Date类型
                    } else method = clazz.getMethod(setMethod, value.getClass());
                    method.invoke(obj, value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
}
