package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.DispatchFilter;
import ru.job4j.Filter;
import ru.job4j.model.Post;

import java.util.List;

@Service("dispatchFilterServiceImpl")
public class DispatchFilterServiceImpl implements DispatchFilterService {

    @Autowired
    private  DispatchFilter dispatcher;

    @Override
    public List<Post> sent(final Filter filter, final String parameter) {
        return dispatcher.init().sent(filter, parameter);
    }
}
