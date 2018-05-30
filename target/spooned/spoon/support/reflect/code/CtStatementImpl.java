package spoon.support.reflect.code;


public abstract class CtStatementImpl extends spoon.support.reflect.code.CtCodeElementImpl implements spoon.reflect.code.CtStatement {
    private static final long serialVersionUID = 1L;

    public static void insertAfter(spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatement statement) throws spoon.reflect.declaration.ParentNotInitializedException {
        spoon.reflect.code.CtStatementList sts = target.getFactory().Core().createStatementList();
        sts.addStatement(statement);
        spoon.support.reflect.code.CtStatementImpl.insertAfter(target, sts);
    }

    public static void insertAfter(spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatementList statements) throws spoon.reflect.declaration.ParentNotInitializedException {
        spoon.reflect.declaration.CtElement e = target.getParent();
        if (e instanceof spoon.reflect.declaration.CtExecutable) {
            throw new java.lang.RuntimeException("cannot insert in this context (use insertEnd?)");
        }
        new spoon.support.reflect.code.CtStatementImpl.InsertVisitor(target, statements, spoon.support.reflect.code.CtStatementImpl.InsertType.AFTER).scan(e);
    }

    public static void insertBefore(spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatement statement) throws spoon.reflect.declaration.ParentNotInitializedException {
        spoon.reflect.code.CtStatementList sts = target.getFactory().Core().createStatementList();
        sts.addStatement(statement);
        spoon.support.reflect.code.CtStatementImpl.insertBefore(target, sts);
    }

    public static void insertBefore(spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatementList statementsToBeInserted) throws spoon.reflect.declaration.ParentNotInitializedException {
        spoon.reflect.declaration.CtElement targetParent = target.getParent();
        if (targetParent instanceof spoon.reflect.declaration.CtExecutable) {
            throw new spoon.SpoonException("cannot insert in this context (use insertEnd?)");
        }
        try {
            if ((target.getParent(spoon.reflect.declaration.CtConstructor.class)) != null) {
                if ((target instanceof spoon.reflect.code.CtInvocation) && (((spoon.reflect.code.CtInvocation<?>) (target)).getExecutable().getSimpleName().startsWith(spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME))) {
                    throw new spoon.SpoonException("cannot insert a statement before a super or this invocation.");
                }
            }
        } catch (spoon.reflect.declaration.ParentNotInitializedException ignore) {
        }
        new spoon.support.reflect.code.CtStatementImpl.InsertVisitor(target, statementsToBeInserted, spoon.support.reflect.code.CtStatementImpl.InsertType.BEFORE).scan(targetParent);
    }

    private static class InsertVisitor extends spoon.reflect.visitor.CtInheritanceScanner {
        private final spoon.reflect.code.CtStatement target;

        private final spoon.reflect.code.CtStatementList statementsToBeInserted;

        private final spoon.support.reflect.code.CtStatementImpl.InsertType insertType;

        InsertVisitor(spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatementList statementsToBeInserted, spoon.support.reflect.code.CtStatementImpl.InsertType insertType) {
            this.target = target;
            this.statementsToBeInserted = statementsToBeInserted;
            this.insertType = insertType;
        }

        @java.lang.Override
        public <R> void visitCtBlock(spoon.reflect.code.CtBlock<R> e) {
            super.visitCtBlock(e);
            insertType.insertFromFirstStatement(e, target, statementsToBeInserted);
        }

        @java.lang.Override
        public void visitCtIf(spoon.reflect.code.CtIf e) {
            super.visitCtIf(e);
            boolean inThen = true;
            spoon.reflect.code.CtStatement stat = e.getThenStatement();
            if (stat != (target)) {
                stat = e.getElseStatement();
                inThen = false;
            }
            if (stat != (target)) {
                throw new java.lang.IllegalArgumentException("should not happen");
            }
            if (stat instanceof spoon.reflect.code.CtBlock) {
                insertType.insert(((spoon.reflect.code.CtBlock<?>) (stat)), statementsToBeInserted);
            }else {
                spoon.reflect.code.CtBlock<?> block = insertNewBlock(stat);
                if (inThen) {
                    e.setThenStatement(block);
                }else {
                    e.setElseStatement(block);
                }
            }
        }

        @java.lang.Override
        public <E> void visitCtSwitch(spoon.reflect.code.CtSwitch<E> e) {
            super.visitCtSwitch(e);
            for (spoon.reflect.code.CtStatement s : statementsToBeInserted) {
                if (!(s instanceof spoon.reflect.code.CtCase)) {
                    throw new java.lang.RuntimeException("cannot insert something that is not case in a switch");
                }
            }
            e.setCases(insertType.insertFromLastStatement(e.getCases(), target, statementsToBeInserted));
        }

        @java.lang.Override
        public <E> void visitCtCase(spoon.reflect.code.CtCase<E> e) {
            super.visitCtCase(e);
            target.setParent(e);
            e.setStatements(insertType.insertFromLastStatement(e.getStatements(), target, statementsToBeInserted));
        }

        @java.lang.Override
        public void scanCtLoop(spoon.reflect.code.CtLoop loop) {
            super.scanCtLoop(loop);
            spoon.reflect.code.CtStatement stat = loop.getBody();
            if (stat instanceof spoon.reflect.code.CtBlock) {
                insertType.insert(((spoon.reflect.code.CtBlock<?>) (stat)), statementsToBeInserted);
            }else {
                spoon.reflect.code.CtBlock<?> block = insertNewBlock(stat);
                target.setParent(block);
                loop.setBody(block);
            }
        }

        private spoon.reflect.code.CtBlock<?> insertNewBlock(spoon.reflect.code.CtStatement stat) {
            spoon.reflect.code.CtBlock<?> block = target.getFactory().Core().createBlock();
            block.addStatement(stat);
            insertType.insertFromFirstStatement(block, target, statementsToBeInserted);
            return block;
        }
    }

    private enum InsertType {
        BEFORE {
            @java.lang.Override
            void insert(spoon.reflect.code.CtBlock<?> block, spoon.reflect.code.CtStatementList statementsToBeInserted) {
                block.insertBegin(statementsToBeInserted);
            }

            @java.lang.Override
            void insertFromFirstStatement(spoon.reflect.code.CtBlock<?> block, spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatementList statementsToBeInserted) {
                final java.util.List<spoon.reflect.code.CtStatement> copy = new java.util.ArrayList<>(block.getStatements());
                int indexOfTargetElement = indexOfReference(block.getStatements(), target);
                for (spoon.reflect.code.CtStatement ctStatement : statementsToBeInserted) {
                    copy.add((indexOfTargetElement++), ctStatement);
                }
                block.setStatements(copy);
            }

            @java.lang.Override
            <T extends spoon.reflect.declaration.CtElement> java.util.List<T> insertFromLastStatement(java.util.List<T> statements, spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatementList statementsToBeInserted) {
                final java.util.List<T> copy = new java.util.ArrayList<>(statements);
                int indexOfTargetElement = indexOfReference(statements, target);
                for (int j = (statementsToBeInserted.getStatements().size()) - 1; j >= 0; j--) {
                    copy.add(indexOfTargetElement, ((T) (statementsToBeInserted.getStatements().get(j))));
                }
                return copy;
            }
        }, AFTER {
            @java.lang.Override
            void insert(spoon.reflect.code.CtBlock<?> block, spoon.reflect.code.CtStatementList statementsToBeInserted) {
                block.insertEnd(statementsToBeInserted);
            }

            @java.lang.Override
            void insertFromFirstStatement(spoon.reflect.code.CtBlock<?> block, spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatementList statementsToBeInserted) {
                final java.util.List<spoon.reflect.code.CtStatement> copy = new java.util.ArrayList<>(block.getStatements());
                int indexOfTargetElement = indexOfReference(block.getStatements(), target);
                for (spoon.reflect.code.CtStatement s : statementsToBeInserted) {
                    copy.add((++indexOfTargetElement), s);
                }
                block.setStatements(copy);
            }

            @java.lang.Override
            <T extends spoon.reflect.declaration.CtElement> java.util.List<T> insertFromLastStatement(java.util.List<T> statements, spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatementList statementsToBeInserted) {
                final java.util.List<T> copy = new java.util.ArrayList<>(statements);
                int indexOfTargetElement = (indexOfReference(copy, target)) + 1;
                for (int j = (statementsToBeInserted.getStatements().size()) - 1; j >= 0; j--) {
                    copy.add(indexOfTargetElement, ((T) (statementsToBeInserted.getStatements().get(j))));
                }
                return copy;
            }
        };
        public int indexOfReference(java.util.List statements, spoon.reflect.declaration.CtElement target) {
            int indexOfTargetElement = -1;
            for (int i = 0; i < (statements.size()); i++) {
                if ((statements.get(i)) == target) {
                    indexOfTargetElement = i;
                    break;
                }
            }
            return indexOfTargetElement;
        }

        abstract void insert(spoon.reflect.code.CtBlock<?> block, spoon.reflect.code.CtStatementList statementsToBeInserted);

        abstract void insertFromFirstStatement(spoon.reflect.code.CtBlock<?> block, spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatementList statementsToBeInserted);

        abstract <T extends spoon.reflect.declaration.CtElement> java.util.List<T> insertFromLastStatement(java.util.List<T> statements, spoon.reflect.code.CtStatement target, spoon.reflect.code.CtStatementList statementsToBeInserted);
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatement> T insertBefore(spoon.reflect.code.CtStatement statement) throws spoon.reflect.declaration.ParentNotInitializedException {
        spoon.support.reflect.code.CtStatementImpl.insertBefore(this, statement);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatement> T insertBefore(spoon.reflect.code.CtStatementList statements) throws spoon.reflect.declaration.ParentNotInitializedException {
        spoon.support.reflect.code.CtStatementImpl.insertBefore(this, statements);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatement> T insertAfter(spoon.reflect.code.CtStatement statement) throws spoon.reflect.declaration.ParentNotInitializedException {
        spoon.support.reflect.code.CtStatementImpl.insertAfter(this, statement);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatement> T insertAfter(spoon.reflect.code.CtStatementList statements) throws spoon.reflect.declaration.ParentNotInitializedException {
        spoon.support.reflect.code.CtStatementImpl.insertAfter(this, statements);
        return ((T) (this));
    }

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.LABEL)
    java.lang.String label;

    @java.lang.Override
    public java.lang.String getLabel() {
        return label;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtStatement> T setLabel(java.lang.String label) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.LABEL, label, this.label);
        this.label = label;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtStatement clone() {
        return ((spoon.reflect.code.CtStatement) (super.clone()));
    }
}

