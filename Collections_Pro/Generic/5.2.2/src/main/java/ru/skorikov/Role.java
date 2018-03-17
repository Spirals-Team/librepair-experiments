package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 20.10.17
 * @ version: java_kurs_standart
 */
public class Role extends Base {

    /**
     * Поле класса role.
     */
    private String role;

    /**
     * Конструктор.
     *
     * @param id   Id.
     * @param role role.
     */
    public Role(String id, String role) {
        super(id);
        this.role = role;
    }

    /**
     * Получить role объекта.
     *
     * @return role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Задать role объекта.
     *
     * @param role role.
     */
    public void setRole(String role) {
        this.role = role;
    }

}
