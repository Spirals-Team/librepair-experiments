package spoon.reflect.visitor;


class OperatorHelper {
    private OperatorHelper() {
    }

    public static boolean isPrefixOperator(spoon.reflect.code.UnaryOperatorKind o) {
        return (spoon.reflect.visitor.OperatorHelper.isSufixOperator(o)) == false;
    }

    public static boolean isSufixOperator(spoon.reflect.code.UnaryOperatorKind o) {
        return o.name().startsWith("POST");
    }

    public static java.lang.String getOperatorText(spoon.reflect.code.UnaryOperatorKind o) {
        switch (o) {
            case POS :
                return "+";
            case NEG :
                return "-";
            case NOT :
                return "!";
            case COMPL :
                return "~";
            case PREINC :
                return "++";
            case PREDEC :
                return "--";
            case POSTINC :
                return "++";
            case POSTDEC :
                return "--";
            default :
                throw new spoon.SpoonException(("Unsupported operator " + (o.name())));
        }
    }

    public static java.lang.String getOperatorText(spoon.reflect.code.BinaryOperatorKind o) {
        switch (o) {
            case OR :
                return "||";
            case AND :
                return "&&";
            case BITOR :
                return "|";
            case BITXOR :
                return "^";
            case BITAND :
                return "&";
            case EQ :
                return "==";
            case NE :
                return "!=";
            case LT :
                return "<";
            case GT :
                return ">";
            case LE :
                return "<=";
            case GE :
                return ">=";
            case SL :
                return "<<";
            case SR :
                return ">>";
            case USR :
                return ">>>";
            case PLUS :
                return "+";
            case MINUS :
                return "-";
            case MUL :
                return "*";
            case DIV :
                return "/";
            case MOD :
                return "%";
            case INSTANCEOF :
                return "instanceof";
            default :
                throw new spoon.SpoonException(("Unsupported operator " + (o.name())));
        }
    }
}

