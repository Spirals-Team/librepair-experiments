package com.almundo.test.callcenter.entities;

import javax.persistence.*;

@Entity
public class Employee {

    public static enum Type {
        OPERATOR, SUPERVISOR , MANAGER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 15, nullable = false)
    private Type type;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Call currentCall;

    @Column(name = "email", length = 100)
    private String email;

    public Employee(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Call getCurrentCall() {
        return currentCall;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCurrentCall(Call currentCall) {
        this.currentCall = currentCall;
    }

}
