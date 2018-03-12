package ru.job4j.profession;

/**
 * Class Doctor.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 26.09.2017
 */
public class Doctor extends Profession {
    /**
     * Method medicate. Лечит пациента.
     *
     * @param patient принимаем пациента
     * @return вернем сообщение
     */
    public String medicate(Patient patient) {
        return "Доктор " + getName() + " вылечил пациента " + patient.getName()
                + " от заболевания " + patient.getDisease();
    }

    /**
     * Метод aboutMe расскажет о себе.
     * @return вернет текст
     */
    public String aboutMe() {
        return ("Меня зовут " + getName() + ". Мой возраст " + getAge()
                + ". Мой диплом " + getDiploma() + ". Мой профиль " + getProfile());
    }
}
