package spoon.test.template.testclasses.replace;


public class OldPattern {
    public static class Parameters {
        public boolean useStartKeyword;

        public java.lang.String startKeyword;

        public boolean startPrefixSpace;

        public java.lang.String start;

        public boolean startSuffixSpace;

        public boolean nextPrefixSpace;

        public java.lang.String next;

        public boolean nextSuffixSpace;

        public boolean endPrefixSpace;

        public java.lang.String end;

        public spoon.reflect.code.CtBlock<java.lang.Void> statements;

        public spoon.reflect.reference.CtTypeReference<?> entityType;

        public spoon.reflect.reference.CtTypeReference<?> itemType;

        public spoon.reflect.code.CtInvocation<java.lang.Iterable<spoon.test.template.testclasses.replace.Item>> getIterable;
    }

    void patternModel(spoon.test.template.testclasses.replace.OldPattern.Parameters params) throws java.lang.Exception {
        if (params.useStartKeyword) {
            printer.writeSpace().writeKeyword(params.startKeyword).writeSpace();
        }
        try (spoon.reflect.visitor.ListPrinter lp = elementPrinterHelper.createListPrinter(params.startPrefixSpace, params.start, params.startSuffixSpace, params.nextPrefixSpace, params.next, params.nextSuffixSpace, params.endPrefixSpace, params.end)) {
            for (spoon.test.template.testclasses.replace.Item item : params.getIterable.S()) {
                lp.printSeparatorIfAppropriate();
                params.statements.S();
            }
        }
    }

    public static spoon.pattern.Pattern createPatternFromMethodPatternModel(spoon.reflect.factory.Factory factory) {
        spoon.reflect.declaration.CtType<?> type = factory.Type().get(spoon.test.template.testclasses.replace.OldPattern.class);
        return spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setBodyOfMethod("patternModel").getPatternElements()).configurePatternParameters(( pb) -> pb.byFieldAccessOnVariable("params").byFieldAccessOnVariable("item").parameter("statements").setContainerKind(spoon.reflect.meta.ContainerKind.LIST)).configurePatternParameters().configureInlineStatements(( ls) -> ls.inlineIfOrForeachReferringTo("useStartKeyword")).build();
    }

    private spoon.test.template.testclasses.replace.ElementPrinterHelper elementPrinterHelper;

    private spoon.reflect.visitor.TokenWriter printer;
}

