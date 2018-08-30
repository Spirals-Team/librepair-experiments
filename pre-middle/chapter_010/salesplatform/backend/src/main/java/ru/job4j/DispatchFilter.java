package ru.job4j;

import org.springframework.stereotype.Component;
import ru.job4j.model.Post;
import ru.job4j.service.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Component
public class DispatchFilter {

    private final Map<Filter, BiFunction<Filter, String, List<Post>>> dispatch = new HashMap<>();
    private PostService service;

    public DispatchFilter(final PostService service) {
        this.service = service;
    }

    public BiFunction<Filter, String, List<Post>> findTodaysPosts() {
        return (filter, param) -> service.findTodaysPosts();
    }

    public BiFunction<Filter, String, List<Post>> findWithPics() {
        return (filter, param) -> service.findWithPics();
    }

    public BiFunction<Filter, String, List<Post>> findByCarManufacture() {
        return (filter, param) -> service.findByCarManufacture(param);
    }

    public DispatchFilter init() {
        this.load(Filter.PUBLISH_DATE, this.findTodaysPosts());
        this.load(Filter.WITH_PICS, this.findWithPics());
        this.load(Filter.MANUFACTURE, this.findByCarManufacture());
        return this;
    }

    public void load(Filter type, BiFunction<Filter, String, List<Post>> handle) {
        this.dispatch.put(type, handle);
    }

    public List<Post> sent(final Filter filter, final String param) {
        return this.dispatch.get(filter).apply(filter, param);
    }
}
