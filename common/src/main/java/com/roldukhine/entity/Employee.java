package com.roldukhine.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name")
    private String lastName;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "icq")
    private String icq;

    @Column(name = "skype")
    private String skype;

    @Column(name = "note")
    private String note;

    @Transient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "work_address")
    private String workAddress;

    @Column(name = "home_address")
    private String homeAddress;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_to_phone", joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id"))
    private List<Phone> phoneList = new ArrayList<>();

    @Column(name = "photo")
    private byte[] photo;

    public void addPhoneToPhoneList(Phone phone) {
        phoneList.add(phone);
    }
}
