package ru.job4j.profession;

/**
 * Class Patient.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 26.09.2017
 */
public class Patient {
    /**
     * Имя пациента.
     */
    private String name;
    /**
     * Заболевание.
     */
    private String disease;

    /**
     * Метод getName.
     *
     * @return вернем имя пацинета
     */
    public String getName() {
        return name;
    }

    /**
     * Метод setName.
     *
     * @param name задаем имя пациенту
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод getDisease.
     *
     * @return вернем заболевание пациента
     */
    public String getDisease() {
        return disease;
    }

    /**
     * Метод setDisease.
     *
     * @param disease задаем заболевание пацинету
     */
    public void setDisease(String disease) {
        this.disease = disease;
    }
}
