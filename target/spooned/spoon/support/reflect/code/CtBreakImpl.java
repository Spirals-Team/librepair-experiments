package spoon.support.reflect.code;


public class CtBreakImpl extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtBreak {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TARGET_LABEL)
    java.lang.String targetLabel;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtBreak(this);
    }

    @java.lang.Override
    public java.lang.String getTargetLabel() {
        return targetLabel;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtLabelledFlowBreak> T setTargetLabel(java.lang.String targetLabel) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TARGET_LABEL, targetLabel, this.targetLabel);
        this.targetLabel = targetLabel;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtStatement getLabelledStatement() {
        java.util.List<spoon.reflect.code.CtStatement> listParents = this.map(new spoon.reflect.visitor.filter.ParentFunction().includingSelf(true)).list();
        for (spoon.reflect.declaration.CtElement parent : listParents) {
            if (parent instanceof spoon.reflect.code.CtStatement) {
                spoon.reflect.code.CtStatement statement = ((spoon.reflect.code.CtStatement) (parent));
                if (((statement.getLabel()) != null) && (statement.getLabel().equals(this.getTargetLabel()))) {
                    return statement;
                }
            }
        }
        return null;
    }

    @java.lang.Override
    public spoon.reflect.code.CtBreak clone() {
        return ((spoon.reflect.code.CtBreak) (super.clone()));
    }
}

