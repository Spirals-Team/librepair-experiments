package ru.job4j.generic;

public class Role extends Base {

    private String role;

    public Role(String id, String role) {
        super(id);
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    void setId(String id) { }
}
