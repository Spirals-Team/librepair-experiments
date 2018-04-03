package net.mirwaldt.jcomparison.primitive.mutable.api;

public interface MutableObject extends Mutable<Object, MutableObject> {
    Object getObject();
    void setObject(Object object);
    
    default byte getType() {
        return TYPE_OBJECT;
    }
}
