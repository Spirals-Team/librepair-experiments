package ru.javawebinar.topjava.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

abstract class IdentifiedEntityTest {
    static final int ID = 1;
    private IdentifiedEntity entity;

    void setEntity(IdentifiedEntity entity) {
        assert entity != null;
        this.entity = entity;
    }

    @Test
    public void newIfIdZero() {
        assertTrue(entity.isNew());
    }

    @Test
    public void nonNewIfIdNonZero() {
        entity.setId(ID);
        assertFalse(entity.isNew());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setZeroId() {
        entity.setId(0);
    }

    @Test(expected = IllegalStateException.class)
    public void setIdToPreviouslyAssigned() {
        entity.setId(ID);
        entity.setId(100);
    }

    @Test
    public void setGetIdEqual() {
        entity.setId(ID);
        assertEquals((Integer)ID, entity.getId());
    }

    @Test
    public void setIdReturnsThis() {
        assertSame(entity, entity.setId(ID));
    }

    @Test(expected = IllegalStateException.class)
    @SuppressWarnings("SelfEquals")
    public void equalsZeroId() {
        entity.equals(entity);
    }

    @Test(expected = IllegalStateException.class)
    public void hashCodeZeroId() {
        entity.hashCode();
    }

    @Test
    @SuppressWarnings("SelfEquals")
    public void equalsThis() {
        entity.setId(ID);
        assertTrue(entity.equals(entity));
    }

    @Test
    public void equalsNull() {
        entity.setId(ID);
        boolean equals = entity.equals(null);
        assertFalse(equals);
    }
}
