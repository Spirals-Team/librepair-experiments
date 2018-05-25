package spoon.reflect.code;


public interface CtLabelledFlowBreak extends spoon.reflect.code.CtCFlowBreak {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TARGET_LABEL)
    java.lang.String getTargetLabel();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TARGET_LABEL)
    <T extends spoon.reflect.code.CtLabelledFlowBreak> T setTargetLabel(java.lang.String targetLabel);

    @spoon.support.DerivedProperty
    spoon.reflect.code.CtStatement getLabelledStatement();
}

