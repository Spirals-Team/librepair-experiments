package ru.siksmfp.basic.structure.cloning.domain;

import ru.siksmfp.basic.structure.utils.cloning.IFreezable;

public class F implements IFreezable {
    private boolean frozen = false;

    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isFrozen() {
        return frozen;
    }
}
