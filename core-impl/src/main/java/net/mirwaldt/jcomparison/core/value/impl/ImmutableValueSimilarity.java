package net.mirwaldt.jcomparison.core.value.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;

import java.util.NoSuchElementException;

public class ImmutableValueSimilarity<ValueType> implements ValueSimilarity<ValueType> {
    private final boolean isOneSimilarValue;
    private final ValueType similarValue;
    private final Pair<ValueType> similarValuesPair;

    private ImmutableValueSimilarity(boolean isOneSimilarValue, ValueType similarValue, Pair<ValueType> similarValuesPair) {
        this.isOneSimilarValue = isOneSimilarValue;
        this.similarValue = similarValue;
        this.similarValuesPair = similarValuesPair;
    }

    public static <ValueType> ImmutableValueSimilarity createValueSimilarityWithValue(
            ValueType similarValue) {
        return new ImmutableValueSimilarity<ValueType>(true, similarValue, null);
    }

    public static <ValueType> ImmutableValueSimilarity createValueSimilarityWithPair(
            Pair<ValueType> similarValuesPair) {
        if(similarValuesPair == null) {
            throw new NullPointerException("null not allowed as single similar value pair.");
        }
        return new ImmutableValueSimilarity<ValueType>(false, null, similarValuesPair);
    }
    
    @Override
    public boolean isOneSimilarValue() {
        return isOneSimilarValue;
    }

    @Override
    public ValueType getSimilarValue() {
        if(isOneSimilarValue) {
            return similarValue;
        } else {
            throw new NoSuchElementException("No single similar value available. Use getSimilarValues()!");
        }
    }

    @Override
    public Pair<ValueType> getSimilarValues() {
        if(!isOneSimilarValue) {
            return similarValuesPair;
        } else {
            throw new NoSuchElementException("No similar values pair available. Use getSimilarValue()!");
        }
    }

    @Override
    public String toString() {
        if(isOneSimilarValue) {
            return similarValue.toString();
        } else {
            return similarValuesPair.toString();
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableValueSimilarity<?> that = (ImmutableValueSimilarity<?>) o;

        if (isOneSimilarValue != that.isOneSimilarValue) return false;
        if (similarValue != null ? !similarValue.equals(that.similarValue) : that.similarValue != null) return false;
        return similarValuesPair != null ? similarValuesPair.equals(that.similarValuesPair) : that.similarValuesPair == null;
    }

    @Override
    public int hashCode() {
        int result = (isOneSimilarValue ? 1 : 0);
        result = 31 * result + (similarValue != null ? similarValue.hashCode() : 0);
        result = 31 * result + (similarValuesPair != null ? similarValuesPair.hashCode() : 0);
        return result;
    }

}
