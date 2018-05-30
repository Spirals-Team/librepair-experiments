package spoon.test.template.testclasses;


public class ReturnReplaceTemplate extends spoon.template.ExtensionTemplate {
    public java.lang.String method() throws java.lang.Throwable {
        return _statement_.S();
    }

    spoon.template.TemplateParameter<java.lang.String> _statement_;

    @spoon.template.Local
    public ReturnReplaceTemplate(spoon.template.TemplateParameter<java.lang.String> statement) {
        this._statement_ = statement;
    }

    @spoon.template.Local
    java.lang.String sample() {
        if (((java.lang.System.currentTimeMillis()) % 2L) == 0) {
            return "Panna";
        }else {
            return "Orel";
        }
    }
}

