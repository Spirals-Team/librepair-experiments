package ua.epam.spring.hometask.domain;

public class UserAccount extends DomainObject{
    private Long userId;
    private String name;
    private double money;

    public UserAccount() {
    }

    public UserAccount(Long userId, String name, double money) {
        this.userId = userId;
        this.name = name;
        this.money = money;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean canBuy(double price) {
        return money - price > 0;
    }

    public void addMoney(double amount) {
        money += amount;
    }

    public boolean buy(double price) {
        if (canBuy(price)) {
            money -= price;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
