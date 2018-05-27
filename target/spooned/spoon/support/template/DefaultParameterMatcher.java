package spoon.support.template;


public class DefaultParameterMatcher implements spoon.support.template.ParameterMatcher {
    public boolean match(spoon.template.TemplateMatcher templateMatcher, spoon.reflect.declaration.CtElement template, spoon.reflect.declaration.CtElement toMatch) {
        return true;
    }
}

