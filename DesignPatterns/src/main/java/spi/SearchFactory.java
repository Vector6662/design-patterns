package spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SearchFactory {
    private SearchFactory(){}

    public static Search newSearch(){
        Search search = null;
        ServiceLoader<Search> loader = ServiceLoader.load(Search.class);
        for (Search value : loader){
            System.out.println(value.getClass());
            search = value;
        }
        return search;
    }
}   
