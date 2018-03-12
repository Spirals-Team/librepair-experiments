package ru.job4j.profession;

/**
 * Class Teacher.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 26.09.2017
 */
public class Teacher extends Profession {
    /**
     * Method teach. учим студента.
     *
     * @param student принимаем студента
     * @return вернем сообщение
     */
    public String teach(Student student) {
        return "Преподаватель " + getName() + " учит студента " + student.getName();
    }

    /**
     * Метод aboutMe расскажет о себе.
     *
     * @return вернет текст
     */
    public String aboutMe() {
        return ("Меня зовут " + getName() + ". Мой возраст " + getAge()
                + ". Мой диплом " + getDiploma() + ". Мой профиль " + getProfile());
    }
}
