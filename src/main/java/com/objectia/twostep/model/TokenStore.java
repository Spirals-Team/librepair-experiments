package com.objectia.twostep.model;

public interface TokenStore {
    Token load();

    void save(Token token);

    void clear();
}