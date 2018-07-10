package ru.job4j;

import ru.job4j.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class DispatchFilter {

    private final Map<Filter, BiFunction<Filter, String, List<Post>>> dispatch = new HashMap<>();

    public BiFunction<Filter, String, List<Post>> findTodaysPosts() {
        return (filter, param) -> new PostRepository().findTodaysPosts();
    }

    public BiFunction<Filter, String, List<Post>> findWithPics() {
        return (filter, param) -> new PostRepository().findWithPics();
    }

    public BiFunction<Filter, String, List<Post>> findByCarManufacture() {
        return (filter, param) -> new PostRepository().findByCarManufacture(param);
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
