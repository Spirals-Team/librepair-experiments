package com.roldukhine.entity;

public enum PhoneType {
    WORK(1), HOME(2);

    private final int tag;

    PhoneType(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }
}
