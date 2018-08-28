package com.roldukhine.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Entity
@ToString
@Table(name = "department")
public class Department extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
}
