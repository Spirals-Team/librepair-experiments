package com.github.javaparser.symbolsolver.resolution.typeinference.constraintformulas;

import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.resolution.typeinference.BoundSet;
import com.github.javaparser.symbolsolver.resolution.typeinference.ConstraintFormula;

import static com.github.javaparser.symbolsolver.resolution.typeinference.TypeHelper.isProperType;

/**
 * A type argument S is contained by a type argument T
 *
 * @author Federico Tomassetti
 */
public class TypeContainedByType extends ConstraintFormula {
    private ResolvedType S;
    private ResolvedType T;

    @Override
    public ReductionResult reduce(BoundSet currentBoundSet) {
        // A constraint formula of the form ‹S <= T›, where S and T are type arguments (§4.5.1), is reduced as follows:
        //
        // - If T is a type:

        if (isProperType(T) && !T.isWildcard()) {

            //   - If S is a type, the constraint reduces to ‹S = T›.
            //
            //   - If S is a wildcard, the constraint reduces to false.

            throw new UnsupportedOperationException();
        }

        // - If T is a wildcard of the form ?, the constraint reduces to true.

        if (T.isWildcard() && !T.asWildcard().isBounded()) {
            return ReductionResult.trueResult();
        }

        // - If T is a wildcard of the form ? extends T':

        if (T.isWildcard() && T.asWildcard().isExtends()) {

            //   - If S is a type, the constraint reduces to ‹S <: T'›.
            //
            //   - If S is a wildcard of the form ?, the constraint reduces to ‹Object <: T'›.
            //
            //   - If S is a wildcard of the form ? extends S', the constraint reduces to ‹S' <: T'›.
            //
            //   - If S is a wildcard of the form ? super S', the constraint reduces to ‹Object = T'›.

            throw new UnsupportedOperationException();
        }

        // - If T is a wildcard of the form ? super T':

        if (T.isWildcard() && T.asWildcard().isSuper()) {

            //   - If S is a type, the constraint reduces to ‹T' <: S›.
            //
            //   - If S is a wildcard of the form ? super S', the constraint reduces to ‹T' <: S'›.
            //
            //   - Otherwise, the constraint reduces to false.

            throw new UnsupportedOperationException();
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeContainedByType that = (TypeContainedByType) o;

        if (!S.equals(that.S)) return false;
        return T.equals(that.T);
    }

    @Override
    public int hashCode() {
        int result = S.hashCode();
        result = 31 * result + T.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TypeContainedByType{" +
                "S=" + S +
                ", T=" + T +
                '}';
    }
}
