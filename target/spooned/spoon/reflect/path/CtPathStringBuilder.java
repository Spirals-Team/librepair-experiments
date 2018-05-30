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
package spoon.reflect.path;


/**
 * Created by nicolas on 27/08/2015.
 */
public class CtPathStringBuilder {
    private final java.util.regex.Pattern pathPattern = java.util.regex.Pattern.compile("([/.#])([^/.#\\[]+)(\\[([^/.#]*)\\])?");

    private final java.util.regex.Pattern argumentPattern = java.util.regex.Pattern.compile("(\\w+)=([^=\\]]+)");

    private java.lang.Class load(java.lang.String name) throws spoon.reflect.path.CtPathException {
        // try without name
        try {
            return java.lang.Class.forName(name);
        } catch (java.lang.ClassNotFoundException ex) {
        }
        // search in spoon.reflect.declaration
        try {
            return java.lang.Class.forName(("spoon.reflect.declaration." + name));
        } catch (java.lang.ClassNotFoundException ex) {
        }
        // search in
        try {
            return java.lang.Class.forName(("spoon.reflect.code." + name));
        } catch (java.lang.ClassNotFoundException ex) {
            throw new spoon.reflect.path.CtPathException(java.lang.String.format("Unable to locate element with type %s in Spoon model", name));
        }
    }

    /**
     * Build path from a string representation.
     *
     * for example:
     * new CtPathBuilder().fromString(".spoon.test.path.Foo.foo#statement[index=0]")
     * Match the first statement of method foo from class spoon.test.path.Foo.
     *
     * Some specials characters
     * . :  match with the given name
     * # : match with a CtPathRole
     * / : match with a element type (for example, to match all classes, use /CtClass
     */
    public spoon.reflect.path.CtPath fromString(java.lang.String pathStr) throws spoon.reflect.path.CtPathException {
        java.util.regex.Matcher matcher = pathPattern.matcher(pathStr);
        spoon.reflect.path.impl.CtPathImpl path = new spoon.reflect.path.impl.CtPathImpl();
        while (matcher.find()) {
            java.lang.String kind = matcher.group(1);
            spoon.reflect.path.impl.CtPathElement pathElement = null;
            if (spoon.reflect.path.impl.CtNamedPathElement.STRING.equals(kind)) {
                pathElement = new spoon.reflect.path.impl.CtNamedPathElement(matcher.group(2));
            }else
                if (spoon.reflect.path.impl.CtTypedNameElement.STRING.equals(kind)) {
                    pathElement = new spoon.reflect.path.impl.CtTypedNameElement(load(matcher.group(2)));
                }else
                    if (spoon.reflect.path.impl.CtRolePathElement.STRING.equals(kind)) {
                        pathElement = new spoon.reflect.path.impl.CtRolePathElement(spoon.reflect.path.CtRole.fromName(matcher.group(2)));
                    }


            java.lang.String args = matcher.group(4);
            if (args != null) {
                for (java.lang.String arg : args.split(";")) {
                    java.util.regex.Matcher argmatcher = argumentPattern.matcher(arg);
                    if (argmatcher.matches()) {
                        pathElement.addArgument(argmatcher.group(1), argmatcher.group(2));
                    }
                }
            }
            path.addLast(pathElement);
        } 
        return path;
    }
}

