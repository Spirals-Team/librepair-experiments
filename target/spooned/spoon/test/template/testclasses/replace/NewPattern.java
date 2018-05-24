package spoon.test.template.testclasses.replace;


public class NewPattern {
    private void patternModel(spoon.test.template.testclasses.replace.OldPattern.Parameters params) throws java.lang.Exception {
        elementPrinterHelper.printList(params.getIterable.S(), params.startPrefixSpace, params.start, params.startSuffixSpace, params.nextPrefixSpace, params.next, params.nextSuffixSpace, params.endPrefixSpace, params.end, ( v) -> {
            params.statements.S();
        });
    }

    public static spoon.pattern.Pattern createPatternFromNewPattern(spoon.reflect.factory.Factory factory) {
        spoon.reflect.declaration.CtType<?> type = factory.Type().get(spoon.test.template.testclasses.replace.NewPattern.class);
        return spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setBodyOfMethod("patternModel").getPatternElements()).configurePatternParameters().configurePatternParameters(( pb) -> {
            pb.parameter("statements").setContainerKind(spoon.reflect.meta.ContainerKind.LIST);
        }).build();
    }

    private spoon.test.template.testclasses.replace.NewPattern.ElementPrinterHelper elementPrinterHelper;

    interface Entity {
        java.lang.Iterable<spoon.test.template.testclasses.replace.Item> $getItems$();
    }

    interface ElementPrinterHelper {
        void printList(java.lang.Iterable<spoon.test.template.testclasses.replace.Item> $getItems$, boolean startPrefixSpace, java.lang.String start, boolean startSufficSpace, boolean nextPrefixSpace, java.lang.String next, boolean nextSuffixSpace, boolean endPrefixSpace, java.lang.String end, java.util.function.Consumer<spoon.test.template.testclasses.replace.Item> consumer);
    }
}

