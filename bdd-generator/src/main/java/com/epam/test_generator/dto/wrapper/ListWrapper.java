package com.epam.test_generator.dto.wrapper;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

public class ListWrapper<T> {

    private List<T> list;

    public ListWrapper() {
        list = new ArrayList<>();
    }

    public ListWrapper(List<T> list) {
        this.list = list;
    }

    @Valid
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean add(T e) {
        return list.add(e);
    }

    public void clear() {
        list.clear();
    }
}
