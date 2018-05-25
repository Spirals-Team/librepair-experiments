package spoon.test.template;


public class ArrayResizeTemplate extends spoon.template.ExtensionTemplate {
    @spoon.template.Parameter
    int _poolSizeIncrement_;

    @spoon.template.Parameter
    static int _staticPoolSizeIncrement_;

    @spoon.template.Parameter("_field_")
    java.lang.String __field_;

    @spoon.template.Parameter
    java.lang.Class<?> _Type_;

    @spoon.template.Local
    public ArrayResizeTemplate(spoon.reflect.declaration.CtField<?> field, int inc) {
        __field_ = field.getSimpleName();
        _Type_ = field.getType().getActualClass();
        _poolSizeIncrement_ = inc;
        _TargetType_ = field.getDeclaringType().getActualClass();
    }

    int poolSize_ = 100;

    @spoon.template.Local
    spoon.test.template._Type_[] _field_;

    public void resize__field_() {
        spoon.test.template._Type_[] tmp = new spoon.test.template._Type_[(poolSize_) + (_poolSizeIncrement_)];
        java.lang.System.arraycopy(_field_, 0, tmp, 0, poolSize_);
        _field_ = tmp;
    }

    @spoon.template.Parameter
    java.lang.Class<?> _TargetType_;

    static spoon.test.template._TargetType_[] instances = new spoon.test.template._TargetType_[spoon.test.template.ArrayResizeTemplate._staticPoolSizeIncrement_];

    static spoon.test.template._TargetType_[][][] instances3 = new spoon.test.template._TargetType_[spoon.test.template.ArrayResizeTemplate._staticPoolSizeIncrement_][][];
}

