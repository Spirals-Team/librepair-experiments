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
 * Implementation of {@link TokenWriter}, which writes all tokens to {@link PrinterHelper}
 */
public class DefaultTokenWriter implements spoon.reflect.visitor.TokenWriter {
    private final spoon.reflect.visitor.PrinterHelper printerHelper;

    public DefaultTokenWriter(spoon.reflect.visitor.PrinterHelper printerHelper) {
        this.printerHelper = printerHelper;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeOperator(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeSeparator(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeLiteral(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeKeyword(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeIdentifier(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeCodeSnippet(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeComment(spoon.reflect.code.CtComment comment) {
        spoon.reflect.visitor.CommentHelper.printComment(printerHelper, comment);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeln() {
        printerHelper.writeln();
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter incTab() {
        printerHelper.incTab();
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter decTab() {
        printerHelper.decTab();
        return this;
    }

    @java.lang.Override
    public void reset() {
        printerHelper.reset();
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeSpace() {
        printerHelper.writeSpace();
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.PrinterHelper getPrinterHelper() {
        return printerHelper;
    }
}

