package net.mirwaldt.jcomparison.primitive.mutable.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.*;

public class DefaultMutableObject implements MutableObject {
    private Object object;

    public DefaultMutableObject(Object object) {
        checkIfPrimitive(object);
        this.object = object;
    }

    static void checkIfPrimitive(Object object) {
        if(object != null) {
            if(object.getClass().equals(Boolean.class)) {
                throw new IllegalArgumentException(
                        "Cannot use mutable object because argument '" + object + "' is of type '" + Boolean.class.getSimpleName() + 
                                "'. Please use a mutuable of type '" + MutableBoolean.class + "'.");
            } else if(object.getClass().equals(Byte.class)) {
                throw new IllegalArgumentException(
                        "Cannot use mutable object because argument '" + object + "' is of type '" + Byte.class.getSimpleName() +
                                "'. Please use a mutuable of type '" + MutableByte.class.getSimpleName() + "'.");                
            } else if(object.getClass().equals(Character.class)) {
                throw new IllegalArgumentException(
                        "Cannot use mutable object because argument '" + object + "' is of type '" + Character.class.getSimpleName() +
                                "'. Please use a mutuable of type '" + MutableChar.class.getSimpleName() + "'.");
            } else if(object.getClass().equals(Short.class)) {
                throw new IllegalArgumentException(
                        "Cannot use mutable object because argument '" + object + "' is of type '" + Short.class.getSimpleName() +
                                "'. Please use a mutuable of type '" + MutableShort.class.getSimpleName() + "'.");
            } else if(object.getClass().equals(Integer.class)) {
                throw new IllegalArgumentException(
                        "Cannot use mutable object because argument '" + object + "' is of type '" + Integer.class.getSimpleName() +
                                "'. Please use a mutuable of type '" + MutableInt.class.getSimpleName() + "'.");
            }  else if(object.getClass().equals(Long.class)) {
                throw new IllegalArgumentException(
                        "Cannot use mutable object because argument '" + object + "' is of type '" + Long.class.getSimpleName() +
                                "'. Please use a mutuable of type '" + MutableLong.class.getSimpleName() + "'.");
            } else if(object.getClass().equals(Float.class)) {
                throw new IllegalArgumentException(
                        "Cannot use mutable object because argument '" + object + "' is of type '" + Float.class.getSimpleName() +
                                "'. Please use a mutuable of type '" + MutableFloat.class.getSimpleName() + "'.");
            } else if(object.getClass().equals(Double.class)) {
                throw new IllegalArgumentException(
                        "Cannot use mutable object because argument '" + object + "' is of type '" + Double.class.getSimpleName() +
                                "'. Please use a mutuable of type '" + MutableDouble.class.getSimpleName() + "'.");
            }
        }
    }

    @Override
    public Object get() {
        return object;
    }

    @Override
    public Object getObject() {
        return object;
    }

    @Override
    public void setObject(Object object) {
        checkIfPrimitive(object);
        this.object = object;
    }

    @Override
    public DefaultMutableObject copy() {
        return new DefaultMutableObject(object);
    }

    @Override
    public void copyTo(MutableObject otherMutable) {
        otherMutable.setObject(object);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultMutableObject that = (DefaultMutableObject) o;

        return object != null ? object.equals(that.object) : that.object == null;
    }

    @Override
    public int hashCode() {
        return object != null ? object.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DefaultMutableObject{" +
                "object=" + object +
                '}';
    }
}
