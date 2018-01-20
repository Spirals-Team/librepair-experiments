package ua.com.company.store.model.entity.additional;

import java.util.Date;

/**
 * Created by Владислав on 25.12.2017.
 */
public class UserProduct {
    private String userName;
    private String productTitle;
    private Date date;
    private boolean is_paid;

    public UserProduct(String userName, String productTitle, Date date, boolean is_paid) {
        this.userName = userName;
        this.productTitle = productTitle;
        this.date = date;
        this.is_paid = is_paid;
    }

    public UserProduct() {

    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
