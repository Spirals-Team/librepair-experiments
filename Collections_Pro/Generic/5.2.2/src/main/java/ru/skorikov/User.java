package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 20.10.17
 * @ version: java_kurs_standart
 * Класс User наследник Base.
 */
public class User extends Base {

    /**
     * Поле имя.
     */
    private String name;

    /**
     * Конструктор.
     *
     * @param id   Id.
     * @param name имя.
     */
    public User(String id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * Получить имя.
     *
     * @return имя объекта.
     */
    public String getName() {
        return name;
    }

    /**
     * Задать имя.
     *
     * @param name имя объекта.
     */
    public void setName(String name) {
        this.name = name;
    }

}
