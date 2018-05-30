/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.visitor;


/**
 * Computes source code representation of the Comment literal
 */
class CommentHelper {
    /**
     * RegExp which matches all possible line separators
     */
    private static final java.util.regex.Pattern LINE_SEPARATORS_RE = java.util.regex.Pattern.compile("\\n\\r|\\n|\\r");

    private CommentHelper() {
    }

    static void printComment(spoon.reflect.visitor.PrinterHelper printer, spoon.reflect.code.CtComment comment) {
        java.util.List<spoon.reflect.code.CtJavaDocTag> tags = null;
        if (comment instanceof spoon.reflect.code.CtJavaDoc) {
            tags = ((spoon.reflect.code.CtJavaDoc) (comment)).getTags();
        }
        spoon.reflect.visitor.CommentHelper.printComment(printer, comment.getCommentType(), comment.getContent(), tags);
    }

    static void printComment(spoon.reflect.visitor.PrinterHelper printer, spoon.reflect.code.CtComment.CommentType commentType, java.lang.String content, java.util.Collection<spoon.reflect.code.CtJavaDocTag> javaDocTags) {
        switch (commentType) {
            case FILE :
                printer.write(spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVADOC_START).writeln();
                break;
            case JAVADOC :
                printer.write(spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVADOC_START).writeln();
                break;
            case INLINE :
                printer.write(spoon.reflect.visitor.DefaultJavaPrettyPrinter.INLINE_COMMENT_START);
                break;
            case BLOCK :
                printer.write(spoon.reflect.visitor.DefaultJavaPrettyPrinter.BLOCK_COMMENT_START);
                break;
        }
        switch (commentType) {
            case INLINE :
                printer.write(content);
                break;
            default :
                java.lang.String[] lines = spoon.reflect.visitor.CommentHelper.LINE_SEPARATORS_RE.split(content);
                for (int i = 0; i < (lines.length); i++) {
                    java.lang.String com = lines[i];
                    if (commentType == (spoon.reflect.code.CtComment.CommentType.BLOCK)) {
                        printer.write(com);
                        if ((lines.length) > 1) {
                            printer.writeln();
                        }
                    }else {
                        if ((com.length()) > 0) {
                            printer.write(((spoon.reflect.visitor.DefaultJavaPrettyPrinter.COMMENT_STAR) + com)).writeln();
                        }else {
                            printer.write((" *" + com)).writeln();
                        }
                    }
                }
                /* no trailing space */
                if ((javaDocTags != null) && ((javaDocTags.isEmpty()) == false)) {
                    printer.write(" *").writeln();
                    for (spoon.reflect.code.CtJavaDocTag docTag : javaDocTags) {
                        spoon.reflect.visitor.CommentHelper.printJavaDocTag(printer, docTag);
                    }
                }
                break;
        }
        switch (commentType) {
            case BLOCK :
                printer.write(spoon.reflect.visitor.DefaultJavaPrettyPrinter.BLOCK_COMMENT_END);
                break;
            case FILE :
                printer.write(spoon.reflect.visitor.DefaultJavaPrettyPrinter.BLOCK_COMMENT_END);
                break;
            case JAVADOC :
                printer.write(spoon.reflect.visitor.DefaultJavaPrettyPrinter.BLOCK_COMMENT_END);
                break;
        }
    }

    static void printJavaDocTag(spoon.reflect.visitor.PrinterHelper printer, spoon.reflect.code.CtJavaDocTag docTag) {
        printer.write(spoon.reflect.visitor.DefaultJavaPrettyPrinter.COMMENT_STAR);
        printer.write(spoon.reflect.code.CtJavaDocTag.JAVADOC_TAG_PREFIX);
        printer.write(docTag.getType().name().toLowerCase());
        printer.write(" ");
        if (docTag.getType().hasParam()) {
            printer.write(docTag.getParam()).writeln();
        }
        java.lang.String[] tagLines = spoon.reflect.visitor.CommentHelper.LINE_SEPARATORS_RE.split(docTag.getContent());
        for (int i = 0; i < (tagLines.length); i++) {
            java.lang.String com = tagLines[i];
            if ((i > 0) || (docTag.getType().hasParam())) {
                printer.write(spoon.reflect.visitor.DefaultJavaPrettyPrinter.COMMENT_STAR);
            }
            if (docTag.getType().hasParam()) {
                printer.write("\t\t");
            }
            printer.write(com.trim()).writeln();
        }
    }
}

