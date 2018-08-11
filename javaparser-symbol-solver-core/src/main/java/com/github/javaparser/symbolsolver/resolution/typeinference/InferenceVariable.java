package com.github.javaparser.symbolsolver.resolution.typeinference;

import com.github.javaparser.resolution.declarations.ResolvedTypeParameterDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;

import java.util.LinkedList;
import java.util.List;

/**
 * Are meta-variables for types - that is, they are special names that allow abstract reasoning about types.
 * To distinguish them from type variables, inference variables are represented with Greek letters, principally α.
 *
 * See JLS 18
 *
 * @author Federico Tomassetti
 */
public class InferenceVariable implements ResolvedType {
    private static int unnamedInstantiated = 0;

    private String name;
    private ResolvedTypeParameterDeclaration typeParameterDeclaration;

    public static List<InferenceVariable> instantiate(List<ResolvedTypeParameterDeclaration> typeParameterDeclarations) {
        List<InferenceVariable> inferenceVariables = new LinkedList<>();
        for (ResolvedTypeParameterDeclaration tp : typeParameterDeclarations) {
            inferenceVariables.add(InferenceVariable.unnamed(tp));
        }
        return inferenceVariables;
    }

    public static InferenceVariable unnamed(ResolvedTypeParameterDeclaration typeParameterDeclaration) {
        return new InferenceVariable("__unnamed__" + (unnamedInstantiated++), typeParameterDeclaration);
    }

    public InferenceVariable(String name, ResolvedTypeParameterDeclaration typeParameterDeclaration) {
        this.name = name;
        this.typeParameterDeclaration = typeParameterDeclaration;
    }

    @Override
    public String describe() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InferenceVariable that = (InferenceVariable) o;

        if (!name.equals(that.name)) return false;
        return typeParameterDeclaration != null ? typeParameterDeclaration.equals(that.typeParameterDeclaration)
                : that.typeParameterDeclaration == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (typeParameterDeclaration != null ? typeParameterDeclaration.hashCode() : 0);
        return result;
    }

    @Override
    public boolean isAssignableBy(ResolvedType other) {
        if (other.equals(this)) {
            return true;
        }
        throw new UnsupportedOperationException(
                "We are unable to determine the assignability of an inference variable without knowing the bounds and"
                        + " constraints");
    }

    public ResolvedTypeParameterDeclaration getTypeParameterDeclaration() {
        if (typeParameterDeclaration == null) {
            throw new IllegalStateException("The type parameter declaration was not specified");
        }
        return typeParameterDeclaration;
    }

    @Override
    public String toString() {
        return "InferenceVariable{" +
                "name='" + name + '\'' +
                ", typeParameterDeclaration=" + typeParameterDeclaration +
                '}';
    }

    @Override
    public boolean mention(List<ResolvedTypeParameterDeclaration> typeParameters) {
        return false;
    }
}
