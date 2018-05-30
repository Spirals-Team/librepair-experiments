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
package spoon.support.visitor.equals;


public class EqualsChecker extends spoon.reflect.visitor.CtInheritanceScanner {
    protected spoon.reflect.declaration.CtElement other;

    private boolean isNotEqual;

    private spoon.reflect.path.CtRole notEqualRole;

    public void setOther(spoon.reflect.declaration.CtElement other) {
        this.other = other;
        isNotEqual = false;
    }

    public boolean isNotEqual() {
        return isNotEqual;
    }

    public spoon.reflect.path.CtRole getNotEqualRole() {
        return notEqualRole;
    }

    /**
     *
     *
     * @param role
     * 		the role of the not equal attribute, or null if there is no such role
     */
    protected void setNotEqual(spoon.reflect.path.CtRole role) {
        notEqualRole = role;
        isNotEqual = true;
        throw spoon.support.visitor.equals.NotEqualException.INSTANCE;
    }

    @java.lang.Override
    public void scanCtNamedElement(spoon.reflect.declaration.CtNamedElement e) {
        final spoon.reflect.declaration.CtNamedElement peek = ((spoon.reflect.declaration.CtNamedElement) (this.other));
        if (!(e.getSimpleName().equals(peek.getSimpleName()))) {
            setNotEqual(spoon.reflect.path.CtRole.NAME);
        }
        super.scanCtNamedElement(e);
    }

    @java.lang.Override
    public void scanCtReference(spoon.reflect.reference.CtReference reference) {
        final spoon.reflect.reference.CtReference peek = ((spoon.reflect.reference.CtReference) (this.other));
        if (!(reference.getSimpleName().equals(peek.getSimpleName()))) {
            setNotEqual(spoon.reflect.path.CtRole.NAME);
        }
        super.scanCtReference(reference);
    }

    @java.lang.Override
    public void scanCtStatement(spoon.reflect.code.CtStatement s) {
        final spoon.reflect.code.CtStatement peek = ((spoon.reflect.code.CtStatement) (this.other));
        final java.lang.String leftLabel = s.getLabel();
        final java.lang.String rightLabel = peek.getLabel();
        if ((leftLabel == null) && (rightLabel == null)) {
            super.scanCtStatement(s);
            return;
        }
        if ((leftLabel == null) || (!(leftLabel.equals(rightLabel)))) {
            setNotEqual(spoon.reflect.path.CtRole.LABEL);
        }
        super.scanCtStatement(s);
    }

    @java.lang.Override
    public void scanCtModifiable(spoon.reflect.declaration.CtModifiable m) {
        final spoon.reflect.declaration.CtModifiable peek = ((spoon.reflect.declaration.CtModifiable) (this.other));
        if ((m.getVisibility()) == null) {
            if ((peek.getVisibility()) != null) {
                setNotEqual(spoon.reflect.path.CtRole.MODIFIER);
            }
        }else
            if ((peek.getVisibility()) == null) {
                setNotEqual(spoon.reflect.path.CtRole.MODIFIER);
            }else
                if (!(m.getVisibility().equals(peek.getVisibility()))) {
                    setNotEqual(spoon.reflect.path.CtRole.MODIFIER);
                }


        if ((m.getModifiers().size()) != (peek.getModifiers().size())) {
            setNotEqual(spoon.reflect.path.CtRole.MODIFIER);
        }
        if (!(m.getModifiers().containsAll(peek.getModifiers()))) {
            setNotEqual(spoon.reflect.path.CtRole.MODIFIER);
        }
        super.scanCtModifiable(m);
    }

    @java.lang.Override
    public <T, A extends T> void visitCtAssignment(spoon.reflect.code.CtAssignment<T, A> assignment) {
        if ((!(assignment instanceof spoon.reflect.code.CtOperatorAssignment)) && ((this.other) instanceof spoon.reflect.code.CtOperatorAssignment)) {
            setNotEqual(null);
        }
        super.visitCtAssignment(assignment);
    }

    @java.lang.Override
    public <T, A extends T> void visitCtOperatorAssignment(spoon.reflect.code.CtOperatorAssignment<T, A> assignment) {
        final spoon.reflect.code.CtOperatorAssignment peek = ((spoon.reflect.code.CtOperatorAssignment) (this.other));
        if ((assignment.getKind()) == null) {
            if ((peek.getKind()) != null) {
                setNotEqual(spoon.reflect.path.CtRole.OPERATOR_KIND);
            }
        }else
            if ((peek.getKind()) == null) {
                setNotEqual(spoon.reflect.path.CtRole.OPERATOR_KIND);
            }else
                if (!(assignment.getKind().equals(peek.getKind()))) {
                    setNotEqual(spoon.reflect.path.CtRole.OPERATOR_KIND);
                }


        super.visitCtOperatorAssignment(assignment);
    }

    @java.lang.Override
    public <T> void visitCtBinaryOperator(spoon.reflect.code.CtBinaryOperator<T> e) {
        final spoon.reflect.code.CtBinaryOperator peek = ((spoon.reflect.code.CtBinaryOperator) (this.other));
        if ((e.getKind()) == null) {
            if ((peek.getKind()) != null) {
                setNotEqual(spoon.reflect.path.CtRole.OPERATOR_KIND);
            }
        }else
            if ((peek.getKind()) == null) {
                setNotEqual(spoon.reflect.path.CtRole.OPERATOR_KIND);
            }else
                if (!(e.getKind().equals(peek.getKind()))) {
                    setNotEqual(spoon.reflect.path.CtRole.OPERATOR_KIND);
                }


        super.visitCtBinaryOperator(e);
    }

    @java.lang.Override
    public <T> void visitCtUnaryOperator(spoon.reflect.code.CtUnaryOperator<T> e) {
        final spoon.reflect.code.CtUnaryOperator peek = ((spoon.reflect.code.CtUnaryOperator) (this.other));
        if ((e.getKind()) == null) {
            if ((peek.getKind()) != null) {
                setNotEqual(spoon.reflect.path.CtRole.OPERATOR_KIND);
            }
        }else
            if ((peek.getKind()) == null) {
                setNotEqual(spoon.reflect.path.CtRole.OPERATOR_KIND);
            }else
                if (!(e.getKind().equals(peek.getKind()))) {
                    setNotEqual(spoon.reflect.path.CtRole.OPERATOR_KIND);
                }


        super.visitCtUnaryOperator(e);
    }

    @java.lang.Override
    public <T> void visitCtArrayTypeReference(spoon.reflect.reference.CtArrayTypeReference<T> e) {
        final spoon.reflect.reference.CtArrayTypeReference peek = ((spoon.reflect.reference.CtArrayTypeReference) (this.other));
        if ((e.getDimensionCount()) != (peek.getDimensionCount())) {
            setNotEqual(spoon.reflect.path.CtRole.TYPE);
        }
        super.visitCtArrayTypeReference(e);
    }

    @java.lang.Override
    public void visitCtBreak(spoon.reflect.code.CtBreak e) {
        final spoon.reflect.code.CtBreak peek = ((spoon.reflect.code.CtBreak) (this.other));
        if ((e.getTargetLabel()) == null) {
            if ((peek.getTargetLabel()) != null) {
                setNotEqual(spoon.reflect.path.CtRole.TARGET_LABEL);
            }
        }else
            if ((peek.getTargetLabel()) == null) {
                setNotEqual(spoon.reflect.path.CtRole.TARGET_LABEL);
            }else
                if (!(e.getTargetLabel().equals(peek.getTargetLabel()))) {
                    setNotEqual(spoon.reflect.path.CtRole.TARGET_LABEL);
                }


        super.visitCtBreak(e);
    }

    @java.lang.Override
    public void visitCtContinue(spoon.reflect.code.CtContinue e) {
        final spoon.reflect.code.CtContinue peek = ((spoon.reflect.code.CtContinue) (this.other));
        if ((e.getTargetLabel()) == null) {
            if ((peek.getTargetLabel()) != null) {
                setNotEqual(spoon.reflect.path.CtRole.TARGET_LABEL);
            }
        }else
            if ((peek.getTargetLabel()) == null) {
                setNotEqual(spoon.reflect.path.CtRole.TARGET_LABEL);
            }else
                if (!(e.getTargetLabel().equals(peek.getTargetLabel()))) {
                    setNotEqual(spoon.reflect.path.CtRole.TARGET_LABEL);
                }


        super.visitCtContinue(e);
    }

    @java.lang.Override
    public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> e) {
        final spoon.reflect.reference.CtExecutableReference peek = ((spoon.reflect.reference.CtExecutableReference) (this.other));
        if ((e.isConstructor()) != (peek.isConstructor())) {
            setNotEqual(null);
        }
        super.visitCtExecutableReference(e);
    }

    @java.lang.Override
    public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> e) {
        final spoon.reflect.declaration.CtMethod peek = ((spoon.reflect.declaration.CtMethod) (this.other));
        if ((e.isDefaultMethod()) != (peek.isDefaultMethod())) {
            setNotEqual(spoon.reflect.path.CtRole.MODIFIER);
        }
        super.visitCtMethod(e);
    }

    @java.lang.Override
    public <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> e) {
        final spoon.reflect.declaration.CtParameter peek = ((spoon.reflect.declaration.CtParameter) (this.other));
        if ((e.isVarArgs()) != (peek.isVarArgs())) {
            setNotEqual(spoon.reflect.path.CtRole.MODIFIER);
        }
        super.visitCtParameter(e);
    }

    @java.lang.Override
    public <T> void visitCtLiteral(spoon.reflect.code.CtLiteral<T> e) {
        final spoon.reflect.code.CtLiteral peek = ((spoon.reflect.code.CtLiteral) (this.other));
        if ((e.getValue()) == null) {
            if ((peek.getValue()) != null) {
                setNotEqual(spoon.reflect.path.CtRole.VALUE);
            }
        }else
            if ((peek.getValue()) == null) {
                setNotEqual(spoon.reflect.path.CtRole.VALUE);
            }else
                if (!(e.getValue().equals(peek.getValue()))) {
                    setNotEqual(spoon.reflect.path.CtRole.VALUE);
                }


        super.visitCtLiteral(e);
    }

    @java.lang.Override
    public void visitCtImport(spoon.reflect.declaration.CtImport ctImport) {
        final spoon.reflect.declaration.CtImport peek = ((spoon.reflect.declaration.CtImport) (this.other));
        if ((ctImport.getImportKind()) == null) {
            if ((peek.getImportKind()) != null) {
                setNotEqual(null);
            }
        }else
            if ((peek.getImportKind()) == null) {
                setNotEqual(null);
            }else
                if (!(ctImport.getImportKind().equals(peek.getImportKind()))) {
                    setNotEqual(null);
                }


        super.visitCtImport(ctImport);
    }
}

