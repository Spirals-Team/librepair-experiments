package spoon.test.template.testclasses.match;


public class MatchMultiple {
    public static spoon.pattern.Pattern createPattern(spoon.pattern.Quantifier matchingStrategy, java.lang.Integer minCount, java.lang.Integer maxCount) throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple.class);
        return spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
            pb.parameter("statements").byReferenceName("statements").setContainerKind(spoon.reflect.meta.ContainerKind.LIST);
            if (matchingStrategy != null) {
                pb.setMatchingStrategy(matchingStrategy);
            }
            if (minCount != null) {
                pb.setMinOccurence(minCount);
            }
            if (maxCount != null) {
                pb.setMaxOccurence(maxCount);
            }
            pb.parameter("printedValue").byFilter((spoon.reflect.code.CtLiteral<?> literal) -> "something".equals(literal.getValue()));
        }).build();
    }

    public void matcher1() {
        statements();
        java.lang.System.out.println("something");
    }

    void statements() {
    }

    public void testMatch1() {
        int i = 0;
        i++;
        java.lang.System.out.println(i);
        java.lang.System.out.println("Xxxx");
        java.lang.System.out.println(((java.lang.String) (null)));
        java.lang.System.out.println("last one");
    }
}

