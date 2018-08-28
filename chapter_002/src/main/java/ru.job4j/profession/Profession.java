package ru.job4j.profession;

/**
 * Class Profession.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 26.09.2017
 */
public class Profession {
    /**
     * Имя.
     */
    private String name;
    /**
     * Возраст.
     */
    private int age;
    /**
     * Диплом.
     */
    private String diploma;
    /**
     * Профиль.
     */
    private String profile;

    /**
     * Method getName.
     *
     * @return вернем имя
     */
    public String getName() {
        return name;
    }

    /**
     * Method setName. Задаем имя.
     *
     * @param name имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method getAge.
     *
     * @return вернем возраст
     */
    public int getAge() {
        return age;
    }

    /**
     * Method setAge. Задаем возраст.
     *
     * @param age возраст
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Method getDiploma.
     *
     * @return вернем диплом
     */
    public String getDiploma() {
        return diploma;
    }

    /**
     * Method setDiploma. Задаем диплом.
     *
     * @param diploma диплом
     */
    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    /**
     * Method getProfile.
     *
     * @return вернем профиль
     */
    public String getProfile() {
        return profile;
    }

    /**
     * Method setProfile. Задаем профиль.
     *
     * @param profile профиль
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }
}
