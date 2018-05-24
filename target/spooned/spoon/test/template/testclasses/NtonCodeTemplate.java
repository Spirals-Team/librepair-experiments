package spoon.test.template.testclasses;


public class NtonCodeTemplate extends spoon.template.AbstractTemplate<spoon.reflect.declaration.CtClass<?>> implements spoon.test.template.testclasses._TargetType_ {
    @spoon.template.Parameter
    static int _n_;

    @spoon.template.Parameter
    spoon.reflect.reference.CtTypeReference<?> _TargetType_;

    static spoon.test.template.testclasses._TargetType_[] instances = new spoon.test.template.testclasses._TargetType_[spoon.test.template.testclasses.NtonCodeTemplate._n_];

    static int instanceCount = 0;

    @spoon.template.Local
    public NtonCodeTemplate(spoon.reflect.reference.CtTypeReference<?> targetType, int n) {
        spoon.test.template.testclasses.NtonCodeTemplate._n_ = n;
        _TargetType_ = targetType;
    }

    @spoon.template.Local
    public void initializer() {
        if ((spoon.test.template.testclasses.NtonCodeTemplate.instanceCount) >= (spoon.test.template.testclasses.NtonCodeTemplate._n_)) {
            throw new java.lang.RuntimeException("max number of instances reached");
        }
        spoon.test.template.testclasses.NtonCodeTemplate.instances[((spoon.test.template.testclasses.NtonCodeTemplate.instanceCount)++)] = this;
    }

    public int getInstanceCount() {
        return spoon.test.template.testclasses.NtonCodeTemplate.instanceCount;
    }

    public spoon.test.template.testclasses._TargetType_ getInstance(int i) {
        if (i > (spoon.test.template.testclasses.NtonCodeTemplate._n_))
            throw new java.lang.RuntimeException(("instance number greater than " + (spoon.test.template.testclasses.NtonCodeTemplate._n_)));

        return spoon.test.template.testclasses.NtonCodeTemplate.instances[i];
    }

    public int getMaxInstanceCount() {
        return spoon.test.template.testclasses.NtonCodeTemplate._n_;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtClass<?> apply(spoon.reflect.declaration.CtType<?> ctType) {
        if (ctType instanceof spoon.reflect.declaration.CtClass) {
            spoon.reflect.declaration.CtClass<?> zeClass = ((spoon.reflect.declaration.CtClass) (ctType));
            spoon.template.Substitution.insertAll(zeClass, this);
            for (spoon.reflect.declaration.CtConstructor<?> c : zeClass.getConstructors()) {
                c.getBody().insertEnd(((spoon.reflect.code.CtStatement) (spoon.template.Substitution.substituteMethodBody(zeClass, this, "initializer"))));
            }
            return zeClass;
        }else {
            return null;
        }
    }

    class Test {
        public void _name_() {
        }
    }
}

