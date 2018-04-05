package com.almundo.test.callcenter.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "answered_date")
    private Date answeredDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finished_date")
    private Date finishedDate;

    @ManyToOne(fetch = FetchType.EAGER)
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

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Call call = (Call) o;
        return id == call.id &&
                Objects.equals(createdDate, call.createdDate) &&
                Objects.equals(answeredDate, call.answeredDate) &&
                Objects.equals(finishedDate, call.finishedDate) &&
                Objects.equals(employee, call.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, answeredDate, finishedDate, employee);
    }

    @Override
    public String toString() {
        return "Call{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", answeredDate=" + answeredDate +
                ", finishedDate=" + finishedDate +
                ", employee=" + employee +
                '}';
    }
}
