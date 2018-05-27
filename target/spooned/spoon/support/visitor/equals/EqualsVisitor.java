package spoon.support.visitor.equals;


public class EqualsVisitor extends spoon.reflect.visitor.CtBiScannerDefault {
    public static boolean equals(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        return new spoon.support.visitor.equals.EqualsVisitor().checkEquals(element, other);
    }

    protected final spoon.support.visitor.equals.EqualsChecker checker;

    private spoon.reflect.path.CtRole lastRole = null;

    public EqualsVisitor() {
        this(new spoon.support.visitor.equals.EqualsChecker());
    }

    public EqualsVisitor(spoon.support.visitor.equals.EqualsChecker checker) {
        this.checker = checker;
    }

    @java.lang.Override
    protected void enter(spoon.reflect.declaration.CtElement e) {
        super.enter(e);
        spoon.reflect.declaration.CtElement other = stack.peek();
        checker.setOther(other);
        try {
            checker.scan(e);
        } catch (spoon.support.visitor.equals.NotEqualException ex) {
            fail(((checker.getNotEqualRole()) == null ? lastRole : checker.getNotEqualRole()), e, other);
        }
    }

    protected boolean isNotEqual = false;

    protected spoon.reflect.path.CtRole notEqualRole;

    protected java.lang.Object notEqualElement;

    protected java.lang.Object notEqualOther;

    @java.lang.Override
    protected void biScan(spoon.reflect.path.CtRole role, java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements, java.util.Collection<? extends spoon.reflect.declaration.CtElement> others) {
        if (isNotEqual) {
            return;
        }
        if (elements == null) {
            if (others != null) {
                fail(role, elements, others);
            }
            return;
        }else
            if (others == null) {
                fail(role, elements, others);
                return;
            }

        if ((elements.size()) != (others.size())) {
            fail(role, elements, others);
            return;
        }
        super.biScan(role, elements, others);
    }

    @java.lang.Override
    public void biScan(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        biScan(null, element, other);
    }

    @java.lang.Override
    public void biScan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        if (isNotEqual) {
            return;
        }
        if (element == null) {
            if (other != null) {
                fail(role, element, other);
                return;
            }
            return;
        }else
            if (other == null) {
                fail(role, element, other);
                return;
            }

        if (element == other) {
            return;
        }
        try {
            lastRole = role;
            super.biScan(element, other);
        } catch (java.lang.ClassCastException e) {
            fail(role, element, other);
        } finally {
            lastRole = null;
        }
        return;
    }

    protected boolean fail(spoon.reflect.path.CtRole role, java.lang.Object element, java.lang.Object other) {
        isNotEqual = true;
        notEqualRole = role;
        notEqualElement = element;
        notEqualOther = other;
        return true;
    }

    public boolean checkEquals(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        biScan(element, other);
        return !(isNotEqual);
    }

    public boolean isEqual() {
        return !(isNotEqual);
    }

    public spoon.reflect.path.CtRole getNotEqualRole() {
        return notEqualRole;
    }

    public java.lang.Object getNotEqualElement() {
        return notEqualElement;
    }

    public java.lang.Object getNotEqualOther() {
        return notEqualOther;
    }
}

