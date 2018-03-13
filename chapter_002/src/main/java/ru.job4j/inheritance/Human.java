package ru.job4j.inheritance;

/**
 * human.
 */
public class Human {
    /**
     * name.
     */
    private String name;
    /**
     * age.
     */
    private Integer age;
    /**
     * health.
     */
    private Boolean goodHealth;

    /**
     *
     * @param age age.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     *
     * @param goodHealth health.
     */
    public void setGoodHealth(Boolean goodHealth) {
        this.goodHealth = goodHealth;
    }

    /**
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return health.
     */
    public Boolean getGoodHealth() {
        return goodHealth;
    }

    /**
     *
     * @return age.
     */
    public Integer getAge() {
        return age;
    }

    /**
     *
     * @return name.
     */
    public String getName() {
        return name;
    }
}
