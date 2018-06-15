package ru.job4j.generic;

public class UserStore extends AbstractStore<User> {
    private SimpleArray<User> value;


    protected UserStore(int size) {
        super(size);
    }

    @Override
    public void add(User model) {
        super.add(model);
    }

    @Override
    public boolean replace(String id) {
        return super.replace(id);
    }

    @Override
    public boolean delete(String id) {
        return super.delete(id);
    }

    @Override
    public User findVyId(String id) {
        return super.findVyId(id);
    }

}
