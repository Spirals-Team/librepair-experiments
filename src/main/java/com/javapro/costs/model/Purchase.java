package com.javapro.costs.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by Vladimir on 20.03.2017.
 */
//@Data
@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
//    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 0)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "money")
    private double money;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "category")
    private String category;

    @Column(name = "comment")
    private String comment;

    public Purchase() {
    }

    public Purchase(double money, LocalDate createdDate, String category, String comment) {
        this.money = money;
        this.createdDate = createdDate;
        this.category = category;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isNew() {
        return id != null;
    }
}
