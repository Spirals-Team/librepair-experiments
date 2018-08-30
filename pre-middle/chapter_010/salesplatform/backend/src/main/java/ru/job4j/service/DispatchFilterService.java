package ru.job4j.service;

import ru.job4j.Filter;
import ru.job4j.model.Post;

import java.util.List;

public interface DispatchFilterService {

    List<Post> sent(Filter filter, String parameter);
}
