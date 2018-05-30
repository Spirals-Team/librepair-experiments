package spoon.reflect.visitor.filter;


public class LineFilter extends spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtStatement> {
    public LineFilter() {
        super(spoon.reflect.code.CtStatement.class);
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.code.CtStatement element) {
        if (!(super.matches(element))) {
            return false;
        }
        if (element instanceof spoon.reflect.code.CtBlock) {
            return false;
        }
        spoon.reflect.declaration.CtElement parent;
        try {
            parent = element.getParent();
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return false;
        }
        if (parent instanceof spoon.reflect.code.CtStatementList) {
            return true;
        }
        if (parent instanceof spoon.reflect.code.CtIf) {
            spoon.reflect.code.CtIf anIf = ((spoon.reflect.code.CtIf) (parent));
            return (element.equals(anIf.getThenStatement())) || (element.equals(anIf.getElseStatement()));
        }
        if (parent instanceof spoon.reflect.code.CtLoop) {
            spoon.reflect.code.CtLoop loop = ((spoon.reflect.code.CtLoop) (parent));
            spoon.reflect.code.CtStatement body = loop.getBody();
            if (body == null) {
                return false;
            }
            return body.equals(element);
        }
        return false;
    }
}

