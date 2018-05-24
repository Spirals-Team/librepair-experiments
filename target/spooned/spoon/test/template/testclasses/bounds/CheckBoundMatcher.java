package spoon.test.template.testclasses.bounds;


public class CheckBoundMatcher {
    public spoon.template.TemplateParameter<java.util.Collection<?>> _col_;

    public spoon.template.TemplateParameter<java.lang.Integer> _x_;

    public spoon.template.TemplateParameter<java.lang.Integer> _y_;

    public spoon.template.TemplateParameter<spoon.reflect.code.CtBlock> _block_;

    public spoon.template.TemplateParameter<spoon.reflect.code.CtStatement> _stmt_;

    public void matcher1() {
        if ((_col_.S().size()) > 10)
            throw new java.lang.IndexOutOfBoundsException();

    }

    public void matcher2() {
        if ((_col_.S().size()) > 10)
            java.lang.System.out.println();

    }

    public void matcher3() {
        if ((_x_.S()) > 10)
            throw new java.lang.IndexOutOfBoundsException();

    }

    public void matcher4() {
        if ((_x_.S()) > (_y_.S()))
            throw new java.lang.IndexOutOfBoundsException();

    }

    public void matcher5() {
        if ((_x_.S()) > (_y_.S()))
            _block_.S();

    }

    public void matcher6() {
        if ((_x_.S()) > (_y_.S())) {
            _stmt_.S();
        }
    }

    public void matcher7() {
        if ((_x_.S()) == (_x_.S()))
            _block_.S();

    }

    @spoon.template.Parameter
    java.lang.Object _w_;
}

