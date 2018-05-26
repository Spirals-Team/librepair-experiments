package spoon.support.reflect.code;


public class CtBlockImpl<R> extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtBlock<R> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.STATEMENT)
    private final spoon.support.util.ModelList<spoon.reflect.code.CtStatement> statements = new spoon.support.util.ModelList<spoon.reflect.code.CtStatement>() {
        private static final long serialVersionUID = 1L;

        @java.lang.Override
        protected spoon.reflect.declaration.CtElement getOwner() {
            return spoon.support.reflect.code.CtBlockImpl.this;
        }

        @java.lang.Override
        protected spoon.reflect.path.CtRole getRole() {
            return spoon.reflect.path.CtRole.STATEMENT;
        }

        @java.lang.Override
        protected int getDefaultCapacity() {
            return spoon.reflect.ModelElementContainerDefaultCapacities.BLOCK_STATEMENTS_CONTAINER_DEFAULT_CAPACITY;
        }

        @java.lang.Override
        protected void onSizeChanged(int newSize) {
            if ((isImplicit()) && ((newSize > 1) || (newSize == 0))) {
                setImplicit(false);
            }
        }
    };

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtBlock(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtStatement> getStatements() {
        return this.statements;
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatement> T getStatement(int i) {
        return ((T) (statements.get(i)));
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatement> T getLastStatement() {
        return ((T) (statements.get(((statements.size()) - 1))));
    }

    private boolean shouldInsertAfterSuper() {
        try {
            if ((((getParent()) != null) && ((getParent()) instanceof spoon.reflect.declaration.CtConstructor)) && ((getStatements().size()) > 0)) {
                spoon.reflect.code.CtStatement first = getStatements().get(0);
                if ((first instanceof spoon.reflect.code.CtInvocation) && (((spoon.reflect.code.CtInvocation<?>) (first)).getExecutable().isConstructor())) {
                    return true;
                }
            }
        } catch (spoon.reflect.declaration.ParentNotInitializedException ignore) {
        }
        return false;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertBegin(spoon.reflect.code.CtStatementList statements) {
        if (this.shouldInsertAfterSuper()) {
            getStatements().get(0).insertAfter(statements);
            return ((T) (this));
        }
        java.util.List<spoon.reflect.code.CtStatement> copy = new java.util.ArrayList<>(statements.getStatements());
        statements.setStatements(null);
        this.statements.addAll(0, copy);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertBegin(spoon.reflect.code.CtStatement statement) {
        if (this.shouldInsertAfterSuper()) {
            getStatements().get(0).insertAfter(statement);
            return ((T) (this));
        }
        this.statements.add(0, statement);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertEnd(spoon.reflect.code.CtStatement statement) {
        addStatement(statement);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertEnd(spoon.reflect.code.CtStatementList statements) {
        java.util.List<spoon.reflect.code.CtStatement> tobeInserted = new java.util.ArrayList<>(statements.getStatements());
        statements.setStatements(null);
        this.statements.addAll(this.statements.size(), tobeInserted);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertAfter(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatement statement) {
        for (spoon.reflect.code.CtStatement e : spoon.reflect.visitor.Query.getElements(this, insertionPoints)) {
            e.insertAfter(statement);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertAfter(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatementList statements) {
        for (spoon.reflect.code.CtStatement e : spoon.reflect.visitor.Query.getElements(this, insertionPoints)) {
            e.insertAfter(statements);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertBefore(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatement statement) {
        for (spoon.reflect.code.CtStatement e : spoon.reflect.visitor.Query.getElements(this, insertionPoints)) {
            e.insertBefore(statement);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertBefore(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatementList statements) {
        for (spoon.reflect.code.CtStatement e : spoon.reflect.visitor.Query.getElements(this, insertionPoints)) {
            e.insertBefore(statements);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T setStatements(java.util.List<spoon.reflect.code.CtStatement> statements) {
        this.statements.set(statements);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T addStatement(spoon.reflect.code.CtStatement statement) {
        this.statements.add(statement);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T addStatement(int index, spoon.reflect.code.CtStatement statement) {
        this.statements.add(index, statement);
        return ((T) (this));
    }

    @java.lang.Override
    public void removeStatement(spoon.reflect.code.CtStatement statement) {
        this.statements.remove(statement);
    }

    @java.lang.Override
    public java.util.Iterator<spoon.reflect.code.CtStatement> iterator() {
        return this.statements.iterator();
    }

    @java.lang.Override
    public R S() {
        return null;
    }

    @java.lang.Override
    public spoon.reflect.code.CtBlock<R> clone() {
        return ((spoon.reflect.code.CtBlock<R>) (super.clone()));
    }
}

