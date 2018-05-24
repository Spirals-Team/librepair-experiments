package spoon.reflect.visitor;


abstract class LiteralHelper {
    private LiteralHelper() {
    }

    public static <T> java.lang.String getLiteralToken(spoon.reflect.code.CtLiteral<T> literal) {
        if ((literal.getValue()) == null) {
            return "null";
        }else
            if ((literal.getValue()) instanceof java.lang.Long) {
                return (literal.getValue()) + "L";
            }else
                if ((literal.getValue()) instanceof java.lang.Float) {
                    return (literal.getValue()) + "F";
                }else
                    if ((literal.getValue()) instanceof java.lang.Character) {
                        boolean mayContainsSpecialCharacter = true;
                        spoon.reflect.cu.SourcePosition position = literal.getPosition();
                        if (position.isValidPosition()) {
                            int stringLength = ((position.getSourceEnd()) - (position.getSourceStart())) - 1;
                            mayContainsSpecialCharacter = stringLength != 1;
                        }
                        java.lang.StringBuilder sb = new java.lang.StringBuilder(10);
                        sb.append('\'');
                        spoon.reflect.visitor.LiteralHelper.appendCharLiteral(sb, ((java.lang.Character) (literal.getValue())), mayContainsSpecialCharacter);
                        sb.append('\'');
                        return sb.toString();
                    }else
                        if ((literal.getValue()) instanceof java.lang.String) {
                            boolean mayContainsSpecialCharacters = true;
                            spoon.reflect.cu.SourcePosition position = literal.getPosition();
                            if (position.isValidPosition()) {
                                int stringLength = ((position.getSourceEnd()) - (position.getSourceStart())) - 1;
                                mayContainsSpecialCharacters = (((java.lang.String) (literal.getValue())).length()) != stringLength;
                            }
                            return ("\"" + (spoon.reflect.visitor.LiteralHelper.getStringLiteral(((java.lang.String) (literal.getValue())), mayContainsSpecialCharacters))) + "\"";
                        }else
                            if ((literal.getValue()) instanceof java.lang.Class) {
                                return ((java.lang.Class<?>) (literal.getValue())).getName();
                            }else {
                                return literal.getValue().toString();
                            }





    }

    static void appendCharLiteral(java.lang.StringBuilder sb, java.lang.Character c, boolean mayContainsSpecialCharacter) {
        if (!mayContainsSpecialCharacter) {
            sb.append(c);
        }else
            if ((java.lang.Character.UnicodeBlock.of(c)) != (java.lang.Character.UnicodeBlock.BASIC_LATIN)) {
                if (c < 16) {
                    sb.append(("\\u000" + (java.lang.Integer.toHexString(c))));
                }else
                    if (c < 256) {
                        sb.append(("\\u00" + (java.lang.Integer.toHexString(c))));
                    }else
                        if (c < 4096) {
                            sb.append(("\\u0" + (java.lang.Integer.toHexString(c))));
                        }else {
                            sb.append(("\\u" + (java.lang.Integer.toHexString(c))));
                        }


            }else {
                switch (c) {
                    case '\b' :
                        sb.append("\\b");
                        break;
                    case '\t' :
                        sb.append("\\t");
                        break;
                    case '\n' :
                        sb.append("\\n");
                        break;
                    case '\f' :
                        sb.append("\\f");
                        break;
                    case '\r' :
                        sb.append("\\r");
                        break;
                    case '\"' :
                        sb.append("\\\"");
                        break;
                    case '\'' :
                        sb.append("\\\'");
                        break;
                    case '\\' :
                        sb.append("\\\\");
                        break;
                    default :
                        sb.append((java.lang.Character.isISOControl(c) ? java.lang.String.format("\\u%04x", ((int) (c))) : java.lang.Character.toString(c)));
                }
            }

    }

    static java.lang.String getStringLiteral(java.lang.String value, boolean mayContainsSpecialCharacter) {
        if (!mayContainsSpecialCharacter) {
            return value;
        }else {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(((value.length()) * 2));
            for (int i = 0; i < (value.length()); i++) {
                spoon.reflect.visitor.LiteralHelper.appendCharLiteral(sb, value.charAt(i), mayContainsSpecialCharacter);
            }
            return sb.toString();
        }
    }
}

