package ru.job4j.profession;

/**
 * Profession.
 *
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Profession {
	/**
	 * Имя.
	 */
	private String name;

	/**
	 * Геттер имени.
	 * @return -имя.
	 */
	public String getName() {
		return name;
	}

    /**
     * Конструктор класса.
     * @param name имя.
     * @param age возрост.
     */
    public Profession(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
	 * Сеттер имени.
	 * @param name - имя.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Геттер возроста.
	 * @return возраст.
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Сеттер возроста.
	 * @param age возрост.
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Возраст.

	 */
	private int age;

}
