package ru.job4j.inheritance;

/**
 * vechicle.
 */
public class Vechicle {
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
     * @param engineer engineer.
     */
    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    /**
     *
     * @param goodCondition goodcond.
     */
    public void setGoodCondition(Boolean goodCondition) {
        this.goodCondition = goodCondition;
    }

    /**
     *
     * @param owner owner.
     */
    public void setOwner(Human owner) {
        this.owner = owner;
    }

    /**
     *
     * @return condition.
     */
    public Boolean getGoodCondition() {
        return goodCondition;
    }

    /**
     *
     * @return engineer.
     */
    public Engineer getEngineer() {
        return engineer;
    }

    /**
     *
     * @return owner.
     */
    public Human getOwner() {
        return owner;
    }

    /**
     * equals.
     * @param obj param.
     * @return if equals.
     */
    @Override
    public boolean equals(Object obj) {
//        return super.equals(obj);
        Vechicle testAuto = (Vechicle) obj;
        return this.engineer == testAuto.engineer && this.goodCondition == testAuto.goodCondition && this.owner == testAuto.owner;
    }

    /**
     *
     * @return hascode.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
