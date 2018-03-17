package ru.skorikov;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 04.11.17
 * @ version: java_kurs_standart
 * 1. Создать модель User
 */
public class User {
    /**
     * Field name.
     */
    private String name;
    /**
     * Field children.
     */
    private int children;
    /**
     * Field birthday.
     */
    private Calendar birthday = Calendar.getInstance();

    /**
     * Create new User.
     *
     * @param name     name.
     * @param children children.
     * @param day      dayBirthday
     * @param month    mounthBirthday
     * @param year     yearBirthday.
     */
    public User(String name, int children, int day, int month, int year) {
        this.name = name;
        this.children = children;
        this.birthday.set(year, month - 1, day);

    }

    /**
     * Get User name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set User name.
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get User children.
     *
     * @return children.
     */
    public int getChildren() {
        return children;
    }

    /**
     * Set User children.
     *
     * @param children children.
     */
    public void setChildren(int children) {
        this.children = children;
    }

    /**
     * Get User birthday.
     *
     * @return birthday.
     */
    public Calendar getBirthday() {
        return birthday;
    }

    /**
     * Set birthday.
     *
     * @param day   new Day
     * @param month new Mouth
     * @param year  new Year
     */
    public void setBirthday(int day, int month, int year) {
        this.birthday.set(year, month - 1, day);
    }

}
