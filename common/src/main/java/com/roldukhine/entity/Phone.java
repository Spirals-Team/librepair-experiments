package com.roldukhine.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "phone")
public class Phone extends BaseEntity {

    @Column(name = "number")
    private String number;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private PhoneType type = PhoneType.WORK;

    public int getPhoneTypeTag() {
        return type.getTag();
    }
}
