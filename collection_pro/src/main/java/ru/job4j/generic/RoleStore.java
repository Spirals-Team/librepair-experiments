package ru.job4j.generic;

public class RoleStore  extends AbstractStore<Role>  {


    protected RoleStore(int size) {
        super(size);
    }

    @Override
    public void add(Role model) {
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
    public Role findVyId(String id) {
        return super.findVyId(id);
    }

}
