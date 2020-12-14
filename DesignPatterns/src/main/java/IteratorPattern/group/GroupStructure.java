package IteratorPattern.group;

import IteratorPattern.Main;
import IteratorPattern.lang.Collection;
import IteratorPattern.lang.Iterator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GroupStructure implements Collection<Employee, Link> {
    //组织Id，也是组织链的头部Id
    private String groupId;
    //组织名称
    private String groupName;
    //组织结构：
    private Map<String,Employee> employeeMap = new ConcurrentHashMap<>();//雇员列表
    private Map<String, List<Link>> linkMap = new ConcurrentHashMap<>();//组织结构关系
    private Map<String,String> invertedMap = new ConcurrentHashMap<>();

    public GroupStructure(String groupId,String groupName){
        this.groupId=groupId;
        this.groupName=groupName;
    }

    @Override
    public boolean add(Employee employee) {
        return employeeMap.put(employee.getuId(),employee)!=null;
    }

    @Override
    public boolean remove(Employee employee) {
        /*
        * return employeeMap.remove(employee)!=null; 这个写法其实是错误的，这个问题我在实习的时候就有遇到：
        * 即使是同一个employee对象，他们只是内容或说包含的信息是一样的，但实参employee对象的地址和employeeMap中
        * 对应的那个地址是不一样的。所以一般都是用key来对map中的值进行定位的。
        */
        return employeeMap.remove(employee.getuId())!=null;
    }

    @Override
    public boolean addLink(String key, Link link) {
        invertedMap.put(link.getToId(),link.getFromId());
        if (linkMap.containsKey(key)){
            return linkMap.get(key).add(link);
        } else {
            List<Link> links = new LinkedList<>();
            links.add(link);
            linkMap.put(key,links);
            return true;
        }
    }

    @Override
    public boolean removeLink(String key) {
        return linkMap.remove(key)!=null;
    }

    @Override
    public Iterator<Employee> iterator() {
        // TODO: 2020/12/14 偷个懒，复习的时候来写一下
        return new Iterator<Employee>() {

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Employee next() {
                return null;
            }
        };
    }
}
