package spoon.test.template.testclasses;


public class EnumAccessTemplate extends spoon.template.ExtensionTemplate {
    public void method() throws java.lang.Throwable {
        spoon.test.template.testclasses.EnumAccessTemplate._AnEnum_._value_.name();
    }

    @spoon.template.Parameter("_AnEnum_")
    java.lang.String __AnEnum_;

    @spoon.template.Parameter
    java.lang.Enum _value_;

    @spoon.template.Local
    public EnumAccessTemplate(java.lang.Enum enumValue, spoon.reflect.factory.Factory factory) {
        this._value_ = enumValue;
        this.__AnEnum_ = enumValue.getClass().getCanonicalName();
    }

    @spoon.template.Local
    enum _AnEnum_ {
        @spoon.template.Parameter
        _value_;}
}

