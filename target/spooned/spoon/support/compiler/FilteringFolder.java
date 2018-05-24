package spoon.support.compiler;


public class FilteringFolder extends spoon.support.compiler.VirtualFolder {
    public spoon.support.compiler.FilteringFolder removeAllThatMatch(java.lang.String regex) {
        for (spoon.compiler.SpoonResource f : new java.util.ArrayList<>(files)) {
            if (f.getPath().matches(regex)) {
                files.remove(f);
            }
        }
        return this;
    }
}

