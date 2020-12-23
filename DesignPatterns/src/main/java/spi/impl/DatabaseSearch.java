package spi.impl;

import spi.Search;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSearch implements Search {
    @Override
    public List<?> search(String keyWord) {
        System.out.println("DatabaseSearch, keyWord = " + keyWord);
        return new ArrayList<>();
    }
}
