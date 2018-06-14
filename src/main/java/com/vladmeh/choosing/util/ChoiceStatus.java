package com.vladmeh.choosing.util;

import com.vladmeh.choosing.model.Choice;

public class ChoiceStatus {
    private final Choice choice;
    private final boolean created;

    public ChoiceStatus(Choice choice, boolean update) {
        this.choice = choice;
        this.created = update;
    }

    public Choice getChoice() {
        return choice;
    }

    public boolean isCreated() {
        return created;
    }
}
