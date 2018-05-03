package ru.job4j.phonelist;

/**
 * @autor Alexander
 * этот клас будет у нас описывать экземпляр Абонент
 */
public class Person {
    private String name;
    private String surname;
    private String phone;
    private String adres;

    public Person(String name, String surname, String phone, String adres) {
        this.name = name;
        this.surname = surname;
        this.adres = adres;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdres() {
        return adres;
    }
}
