package ru.job4j.nonBlockingCache;

public class User {
    private String name;
    private Integer id;
    private Integer version;

    public void incrementVersion() {
        version++;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }

    public User(String name, Integer id) {
        this.name = name;
        this.id = id;
        this.version = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
