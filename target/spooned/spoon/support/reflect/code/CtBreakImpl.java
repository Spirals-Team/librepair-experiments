/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
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

