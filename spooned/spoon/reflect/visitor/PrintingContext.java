package spoon.reflect.visitor;


public class PrintingContext {
    private long NO_TYPE_DECL = 1 << 0;

    private long IGNORE_GENERICS = 1 << 1;

    private long SKIP_ARRAY = 1 << 2;

    private long IGNORE_STATIC_ACCESS = 1 << 3;

    private long IGNORE_ENCLOSING_CLASS = 1 << 4;

    private long FORCE_WILDCARD_GENERICS = 1 << 5;

    private long state;

    public boolean noTypeDecl() {
        return ((state) & (NO_TYPE_DECL)) != 0L;
    }

    public boolean ignoreGenerics() {
        return ((state) & (IGNORE_GENERICS)) != 0L;
    }

    public boolean skipArray() {
        return ((state) & (SKIP_ARRAY)) != 0L;
    }

    public boolean ignoreStaticAccess() {
        return ((state) & (IGNORE_STATIC_ACCESS)) != 0L;
    }

    public boolean ignoreEnclosingClass() {
        return ((state) & (IGNORE_ENCLOSING_CLASS)) != 0L;
    }

    public boolean forceWildcardGenerics() {
        return ((state) & (FORCE_WILDCARD_GENERICS)) != 0L;
    }

    public class Writable implements java.lang.AutoCloseable {
        private long oldState;

        protected Writable() {
            oldState = state;
        }

        @java.lang.Override
        public void close() {
            state = oldState;
        }

        public <T extends spoon.reflect.visitor.PrintingContext.Writable> T noTypeDecl(boolean v) {
            setState(NO_TYPE_DECL, v);
            return ((T) (this));
        }

        public <T extends spoon.reflect.visitor.PrintingContext.Writable> T ignoreGenerics(boolean v) {
            setState(IGNORE_GENERICS, v);
            return ((T) (this));
        }

        public <T extends spoon.reflect.visitor.PrintingContext.Writable> T skipArray(boolean v) {
            setState(SKIP_ARRAY, v);
            return ((T) (this));
        }

        public <T extends spoon.reflect.visitor.PrintingContext.Writable> T ignoreStaticAccess(boolean v) {
            setState(IGNORE_STATIC_ACCESS, v);
            return ((T) (this));
        }

        public <T extends spoon.reflect.visitor.PrintingContext.Writable> T ignoreEnclosingClass(boolean v) {
            setState(IGNORE_ENCLOSING_CLASS, v);
            return ((T) (this));
        }

        public <T extends spoon.reflect.visitor.PrintingContext.Writable> T forceWildcardGenerics(boolean v) {
            setState(FORCE_WILDCARD_GENERICS, v);
            return ((T) (this));
        }

        private void setState(long mask, boolean v) {
            state = (v) ? (state) | mask : (state) & (~mask);
        }
    }

    public spoon.reflect.visitor.PrintingContext.Writable modify() {
        return new spoon.reflect.visitor.PrintingContext.Writable();
    }

    java.util.Deque<spoon.reflect.visitor.TypeContext> currentThis = new java.util.ArrayDeque<>();

    public spoon.reflect.reference.CtTypeReference<?> getCurrentTypeReference() {
        if ((currentTopLevel) != null) {
            spoon.reflect.visitor.TypeContext tc = getCurrentTypeContext();
            if (tc != null) {
                return tc.typeRef;
            }
            return currentTopLevel.getReference();
        }
        return null;
    }

    private spoon.reflect.visitor.TypeContext getCurrentTypeContext() {
        if (((currentThis) != null) && ((currentThis.size()) > 0)) {
            spoon.reflect.visitor.TypeContext tc = currentThis.peek();
            return tc;
        }
        return null;
    }

    public void pushCurrentThis(spoon.reflect.declaration.CtType<?> type) {
        currentThis.push(new spoon.reflect.visitor.TypeContext(type));
    }

    public void popCurrentThis() {
        currentThis.pop();
    }

    java.util.Deque<spoon.reflect.declaration.CtElement> elementStack = new java.util.ArrayDeque<>();

    java.util.Deque<spoon.reflect.code.CtExpression<?>> parenthesedExpression = new java.util.ArrayDeque<>();

    spoon.reflect.declaration.CtType<?> currentTopLevel;

    @java.lang.Override
    public java.lang.String toString() {
        return ("context.ignoreGenerics: " + (ignoreGenerics())) + "\n";
    }

    public boolean isInCurrentScope(spoon.reflect.reference.CtTypeReference<?> typeRef) {
        spoon.reflect.reference.CtTypeReference<?> currentTypeRef = getCurrentTypeReference();
        return (currentTypeRef != null) && (typeRef.equals(currentTypeRef));
    }
}

