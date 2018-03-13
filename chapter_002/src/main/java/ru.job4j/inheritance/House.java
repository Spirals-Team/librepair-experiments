package ru.job4j.inheritance;

/**
 * house.
 */
public class House {
    /**
     * owner.
     */
    private Human owner;
    /**
     * engineer.
     */
    private Engineer engineer;
    /**
     * condition.
     */
    private Boolean goodCondition;

    /**
     *
     * @param owner owner.
     */
    public void setOwner(Human owner) {
        this.owner = owner;
    }

    /**
     *
     * @param goodCondition condition.
     */
    public void setGoodCondition(Boolean goodCondition) {
        this.goodCondition = goodCondition;
    }

    /**
     *
     * @param engineer engineer.
     */
    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    /**
     *
     * @return owner.
     */
    public Human getOwner() {
        return owner;
    }

    /**
     *
     * @return engineer
     */
    public Engineer getEngineer() {
        return engineer;
    }

    /**
     *
     * @return goodCondition
     */
    public Boolean getGoodCondition() {
        return goodCondition;
    }

    /**
     * override.
     * @param obj for checking equalation.
     * @return result.
     */

    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        House testHouse = (House) obj;
        return this.engineer == testHouse.engineer && this.goodCondition == testHouse.goodCondition && this.owner == testHouse.owner;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
