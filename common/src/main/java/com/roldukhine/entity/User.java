package com.roldukhine.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}
