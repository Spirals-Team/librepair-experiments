package spoon.test.template.testclasses;


public class SubstituteRootTemplate extends spoon.template.StatementTemplate {
    @java.lang.Override
    public void statement() throws java.lang.Throwable {
        block.S();
    }

    @spoon.template.Parameter
    spoon.template.TemplateParameter<java.lang.Void> block;

    @spoon.template.Local
    public SubstituteRootTemplate(spoon.reflect.code.CtBlock<java.lang.Void> block) {
        this.block = block;
    }

    @spoon.template.Local
    void sampleBlock() {
        java.lang.String s = "Spoon is cool!";
    }
}

