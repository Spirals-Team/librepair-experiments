package spoon.test.template.testclasses;


public class ArrayAccessTemplate extends spoon.template.ExtensionTemplate {
    public void method() throws java.lang.Throwable {
        blocks[0].S();
        blocks[2].S();
        blocks[1].S();
    }

    public void method2() throws java.lang.Throwable {
        java.lang.System.out.println(strings[1]);
        java.lang.System.out.println(strings[100]);
    }

    @spoon.template.Parameter
    spoon.template.TemplateParameter<spoon.reflect.code.CtBlock<?>>[] blocks;

    @spoon.template.Parameter
    java.lang.String[] strings;

    @spoon.template.Local
    public ArrayAccessTemplate(spoon.template.TemplateParameter<spoon.reflect.code.CtBlock<?>>[] blocks) {
        this.blocks = blocks;
        strings = new java.lang.String[]{ "first", "second" };
    }

    @spoon.template.Local
    void sampleBlocks() {
        {
            int i = 0;
        }
        {
            java.lang.String s = "Spoon is cool!";
        }
    }
}

