package spoon.reflect;


public class Changes {
    java.util.List<spoon.reflect.declaration.CtElement> added;

    java.util.List<spoon.reflect.declaration.CtElement> modified;

    java.util.List<spoon.reflect.declaration.CtElement> removed;

    public Changes() {
    }

    public java.util.List<spoon.reflect.declaration.CtElement> getAdded() {
        if ((added) == null) {
            added = new java.util.ArrayList<>();
        }
        return added;
    }

    public java.util.List<spoon.reflect.declaration.CtElement> getRemoved() {
        if ((removed) == null) {
            removed = new java.util.ArrayList<>();
        }
        return removed;
    }

    public java.util.List<spoon.reflect.declaration.CtElement> getModified() {
        if ((modified) == null) {
            modified = new java.util.ArrayList<>();
        }
        return modified;
    }

    public boolean hasAdded() {
        return ((added) != null) && (!(added.isEmpty()));
    }

    public boolean hasModified() {
        return ((modified) != null) && (!(modified.isEmpty()));
    }

    public boolean hasRemoved() {
        return ((removed) != null) && (!(removed.isEmpty()));
    }
}

