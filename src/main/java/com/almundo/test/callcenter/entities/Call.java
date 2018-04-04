package com.almundo.test.callcenter.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "answered_date")
    private Date answeredDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "finished_date")
    private Date finishedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Call() {
        this.createdDate = new Date();
    }

    public long getId() {
        return id;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public Date getAnsweredDate() {
        return this.answeredDate;
    }

    public Date getFinishedDate() {
        return this.finishedDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedDate(Date endDate) {
        this.finishedDate = endDate;
    }

    public void setAnsweredDate(Date answeredDate) {
        this.answeredDate = answeredDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
