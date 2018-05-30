package spoon.template;


public abstract class AbstractTemplate<T extends spoon.reflect.declaration.CtElement> implements spoon.template.Template<T> {
    private boolean addGeneratedBy = false;

    public boolean isWellFormed() {
        return (spoon.support.template.Parameters.getAllTemplateParameterFields(this.getClass()).size()) > 0;
    }

    public boolean isValid() {
        try {
            for (java.lang.reflect.Field f : spoon.support.template.Parameters.getAllTemplateParameterFields(this.getClass())) {
                if ((f.get(this)) == null) {
                    return false;
                }
            }
            return true;
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException(e);
        }
    }

    public spoon.reflect.factory.Factory getFactory() {
        return spoon.template.Substitution.getFactory(this);
    }

    public boolean isAddGeneratedBy() {
        return addGeneratedBy;
    }

    public spoon.template.AbstractTemplate<T> addGeneratedBy(boolean addGeneratedBy) {
        this.addGeneratedBy = addGeneratedBy;
        return this;
    }
}

