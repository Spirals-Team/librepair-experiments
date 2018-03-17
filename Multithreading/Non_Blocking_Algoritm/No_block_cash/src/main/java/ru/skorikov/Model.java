package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 19.02.18
 * @ version: java_kurs_standart
 */
public class Model {
    /**
     * Версия модели.
     */
    private Integer version;
    /**
     * Имя модели.
     */
    private String name;
    /**
     * Данные модели.
     */
    private String data;

    /**
     * Конструктор.
     * @param name имя.
     * @param data данные.
     */
    public Model(String name, String data) {
        this.version = 0;
        this.name = name;
        this.data = data;
    }

    /**
     * Задать версию.
     *
     * @param version версия
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Получить версию.
     * @return версия.
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Получить имя.
     * @return имя.
     */
    public String getName() {
        return name;
    }

    /**
     * Задать имя.
     * @param name имя.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получить данные.
     * @return данные.
     */
    public String getData() {
        return data;
    }

    /**
     * Задать данные.
     * @param data данные.
     */
    public void setData(String data) {
        this.data = data;
    }
}
