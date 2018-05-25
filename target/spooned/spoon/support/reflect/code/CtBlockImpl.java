package spoon.support.reflect.code;


public class CtBlockImpl<R> extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtBlock<R> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.STATEMENT)
    private java.util.List<spoon.reflect.code.CtStatement> statements = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtBlock(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtStatement> getStatements() {
        ensureModifiableStatementsList();
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
        ensureModifiableStatementsList();
        for (spoon.reflect.code.CtStatement statement : statements.getStatements()) {
            statement.setParent(this);
            this.addStatement(0, statement);
        }
        if ((isImplicit()) && ((this.statements.size()) > 1)) {
            setImplicit(false);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertBegin(spoon.reflect.code.CtStatement statement) {
        if (this.shouldInsertAfterSuper()) {
            getStatements().get(0).insertAfter(statement);
            return ((T) (this));
        }
        ensureModifiableStatementsList();
        statement.setParent(this);
        this.addStatement(0, statement);
        if ((isImplicit()) && ((this.statements.size()) > 1)) {
            setImplicit(false);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertEnd(spoon.reflect.code.CtStatement statement) {
        ensureModifiableStatementsList();
        addStatement(statement);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertEnd(spoon.reflect.code.CtStatementList statements) {
        for (spoon.reflect.code.CtStatement s : statements.getStatements()) {
            insertEnd(s);
        }
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
        if ((statements == null) || (statements.isEmpty())) {
            this.statements = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.STATEMENT, this.statements, new java.util.ArrayList<>(this.statements));
        this.statements.clear();
        for (spoon.reflect.code.CtStatement s : statements) {
            addStatement(s);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T addStatement(spoon.reflect.code.CtStatement statement) {
        return this.addStatement(this.statements.size(), statement);
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T addStatement(int index, spoon.reflect.code.CtStatement statement) {
        if (statement == null) {
            return ((T) (this));
        }
        ensureModifiableStatementsList();
        statement.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.STATEMENT, this.statements, index, statement);
        this.statements.add(index, statement);
        if ((isImplicit()) && ((this.statements.size()) > 1)) {
            setImplicit(false);
        }
        return ((T) (this));
    }

    private void ensureModifiableStatementsList() {
        if ((this.statements) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtStatement>emptyList())) {
            this.statements = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.BLOCK_STATEMENTS_CONTAINER_DEFAULT_CAPACITY);
        }
    }

    @java.lang.Override
    public void removeStatement(spoon.reflect.code.CtStatement statement) {
        if ((this.statements) != (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtStatement>emptyList())) {
            boolean hasBeenRemoved = false;
            for (int i = 0; i < (this.statements.size()); i++) {
                if ((this.statements.get(i)) == statement) {
                    getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.STATEMENT, statements, i, statement);
                    this.statements.remove(i);
                    hasBeenRemoved = true;
                    break;
                }
            }
            if (!hasBeenRemoved) {
                getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.STATEMENT, statements, statements.indexOf(statement), statement);
                this.statements.remove(statement);
            }
            if ((isImplicit()) && ((statements.size()) == 0)) {
                setImplicit(false);
            }
        }
    }

    @java.lang.Override
    public java.util.Iterator<spoon.reflect.code.CtStatement> iterator() {
        if (getStatements().isEmpty()) {
            return spoon.support.util.EmptyIterator.instance();
        }
        return java.util.Collections.unmodifiableList(new java.util.ArrayList<>(getStatements())).iterator();
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

