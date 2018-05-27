package spoon.support.reflect;


public class CtExtendedModifier implements java.io.Serializable {
    private boolean implicit;

    private spoon.reflect.declaration.ModifierKind kind;

    private spoon.reflect.cu.SourcePosition position;

    public CtExtendedModifier(spoon.reflect.declaration.ModifierKind kind) {
        this.kind = kind;
    }

    public CtExtendedModifier(spoon.reflect.declaration.ModifierKind kind, boolean implicit) {
        this(kind);
        this.implicit = implicit;
    }

    public boolean isImplicit() {
        return implicit;
    }

    public void setImplicit(boolean implicit) {
        this.implicit = implicit;
    }

    public spoon.reflect.declaration.ModifierKind getKind() {
        return kind;
    }

    public void setKind(spoon.reflect.declaration.ModifierKind kind) {
        this.kind = kind;
    }

    public spoon.reflect.cu.SourcePosition getPosition() {
        if ((position) == null) {
            return spoon.reflect.cu.SourcePosition.NOPOSITION;
        }
        return position;
    }

    public void setPosition(spoon.reflect.cu.SourcePosition position) {
        this.position = position;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if ((this) == o) {
            return true;
        }
        if ((o == null) || ((getClass()) != (o.getClass()))) {
            return false;
        }
        spoon.support.reflect.CtExtendedModifier that = ((spoon.support.reflect.CtExtendedModifier) (o));
        return ((implicit) == (that.implicit)) && ((kind) == (that.kind));
    }

    @java.lang.Override
    public int hashCode() {
        int result = (implicit) ? 1 : 0;
        result = (31 * result) + ((kind) != null ? kind.hashCode() : 0);
        return result;
    }
}

