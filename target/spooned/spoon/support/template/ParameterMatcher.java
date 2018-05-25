package spoon.support.template;


public interface ParameterMatcher {
    boolean match(spoon.template.TemplateMatcher templateMatcher, spoon.reflect.declaration.CtElement template, spoon.reflect.declaration.CtElement toMatch);
}

