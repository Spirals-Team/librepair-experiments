package spoon.template;


public abstract class BlockTemplate extends spoon.template.AbstractTemplate<spoon.reflect.code.CtBlock<?>> {
    public static spoon.reflect.code.CtBlock<?> getBlock(spoon.reflect.declaration.CtClass<? extends spoon.template.BlockTemplate> p) {
        spoon.reflect.code.CtBlock<?> b = p.getMethod("block").getBody();
        return b;
    }

    public BlockTemplate() {
    }

    public spoon.reflect.code.CtBlock<?> apply(spoon.reflect.declaration.CtType<?> targetType) {
        spoon.reflect.declaration.CtClass<? extends spoon.template.BlockTemplate> c = spoon.template.Substitution.getTemplateCtClass(targetType, this);
        return spoon.template.TemplateBuilder.createPattern(spoon.template.BlockTemplate.getBlock(c), this).setAddGeneratedBy(isAddGeneratedBy()).substituteSingle(targetType, spoon.reflect.code.CtBlock.class);
    }

    public java.lang.Void S() {
        return null;
    }

    public abstract void block() throws java.lang.Throwable;
}

