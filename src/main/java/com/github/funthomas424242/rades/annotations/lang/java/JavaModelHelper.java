package com.github.funthomas424242.rades.annotations.lang.java;

public class JavaModelHelper {

    public static String computePackageName(final String fullQualifiedClassName){
        String packageName=null;
        int lastDot = fullQualifiedClassName.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = fullQualifiedClassName.substring(0, lastDot);
        }
        return packageName;
    }
}
