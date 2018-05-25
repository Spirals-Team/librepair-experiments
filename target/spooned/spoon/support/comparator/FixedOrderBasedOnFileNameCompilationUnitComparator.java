package spoon.support.comparator;


public class FixedOrderBasedOnFileNameCompilationUnitComparator implements java.util.Comparator<org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration> {
    @java.lang.Override
    public int compare(org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration o1, org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration o2) {
        java.lang.String s1 = new java.lang.String(o1.getFileName());
        java.lang.String s2 = new java.lang.String(o2.getFileName());
        return s1.compareTo(s2);
    }
}

