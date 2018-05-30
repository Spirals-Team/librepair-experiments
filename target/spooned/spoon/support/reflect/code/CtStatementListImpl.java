package spoon.support.reflect.code;


public class CtStatementListImpl<R> extends spoon.support.reflect.code.CtCodeElementImpl implements spoon.reflect.code.CtStatementList {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.STATEMENT)
    java.util.List<spoon.reflect.code.CtStatement> statements = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtStatementList(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtStatement> getStatements() {
        return statements;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T setStatements(java.util.List<spoon.reflect.code.CtStatement> stmts) {
        if ((stmts == null) || (stmts.isEmpty())) {
            this.statements = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.STATEMENT, this.statements, new java.util.ArrayList<>(this.statements));
        this.statements.clear();
        for (spoon.reflect.code.CtStatement stmt : stmts) {
            addStatement(stmt);
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
        if ((this.statements) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtStatement>emptyList())) {
            this.statements = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.BLOCK_STATEMENTS_CONTAINER_DEFAULT_CAPACITY);
        }
        statement.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.STATEMENT, this.statements, index, statement);
        this.statements.add(index, statement);
        return ((T) (this));
    }

    private void ensureModifiableStatementsList() {
        if ((this.statements) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtStatement>emptyList())) {
            this.statements = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.BLOCK_STATEMENTS_CONTAINER_DEFAULT_CAPACITY);
        }
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatementList> T insertBegin(spoon.reflect.code.CtStatementList statements) {
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
    public <T extends spoon.reflect.code.CtStatement> T getStatement(int i) {
        return ((T) (statements.get(i)));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatement> T getLastStatement() {
        return ((T) (statements.get(((statements.size()) - 1))));
    }

    @java.lang.Override
    public void removeStatement(spoon.reflect.code.CtStatement statement) {
        if ((this.statements) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtStatement>emptyList())) {
            return;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.STATEMENT, statements, statements.indexOf(statement), statement);
        statements.remove(statement);
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtElement> E setPosition(spoon.reflect.cu.SourcePosition position) {
        for (spoon.reflect.code.CtStatement s : statements) {
            s.setPosition(position);
        }
        return ((E) (this));
    }

    @java.lang.Override
    public java.util.Iterator<spoon.reflect.code.CtStatement> iterator() {
        return statements.iterator();
    }

    @java.lang.Override
    public spoon.reflect.code.CtStatementList clone() {
        return ((spoon.reflect.code.CtStatementList) (super.clone()));
    }

    public spoon.reflect.code.CtStatementList getSubstitution(spoon.reflect.declaration.CtType<?> targetType) {
        return clone();
    }
}

