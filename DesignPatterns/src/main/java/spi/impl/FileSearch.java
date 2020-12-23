package spi.impl;

import spi.Search;

import java.util.ArrayList;
import java.util.List;

public class FileSearch implements Search {
    @Override
    public List<?> search(String keyWord) {
        System.out.println("FileSearch, keyWord = " + keyWord);
        return new ArrayList<>();
    }
}
