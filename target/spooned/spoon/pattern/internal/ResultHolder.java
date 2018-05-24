package spoon.pattern.internal;


public abstract class ResultHolder<T> {
    private final java.lang.Class<T> requiredClass;

    public ResultHolder(java.lang.Class<T> requiredClass) {
        this.requiredClass = requiredClass;
    }

    public java.lang.Class<T> getRequiredClass() {
        return requiredClass;
    }

    public abstract boolean isMultiple();

    public abstract void addResult(T value);

    public abstract void mapEachResult(java.util.function.Function<T, T> consumer);

    public abstract java.util.List<T> getResults();

    public static class Single<T> extends spoon.pattern.internal.ResultHolder<T> {
        private T result;

        public Single(java.lang.Class<T> requiredClass) {
            super(requiredClass);
        }

        @java.lang.Override
        public boolean isMultiple() {
            return false;
        }

        @java.lang.Override
        public void addResult(T value) {
            if ((this.result) != null) {
                throw new spoon.SpoonException("Cannot add second value into single value ConversionContext");
            }
            this.result = value;
        }

        public T getResult() {
            return result;
        }

        @java.lang.Override
        public void mapEachResult(java.util.function.Function<T, T> consumer) {
            result = consumer.apply(result);
        }

        @java.lang.Override
        public java.util.List<T> getResults() {
            return (result) == null ? java.util.Collections.emptyList() : java.util.Collections.singletonList(result);
        }
    }

    public static class Multiple<T> extends spoon.pattern.internal.ResultHolder<T> {
        java.util.List<T> result = new java.util.ArrayList<>();

        public Multiple(java.lang.Class<T> requiredClass) {
            super(requiredClass);
        }

        @java.lang.Override
        public boolean isMultiple() {
            return true;
        }

        @java.lang.Override
        public void addResult(T value) {
            this.result.add(value);
        }

        public java.util.List<T> getResult() {
            return result;
        }

        @java.lang.Override
        public java.util.List<T> getResults() {
            return result;
        }

        @java.lang.Override
        public void mapEachResult(java.util.function.Function<T, T> consumer) {
            for (java.util.ListIterator<T> iter = result.listIterator(); iter.hasNext();) {
                iter.set(consumer.apply(iter.next()));
            }
        }
    }
}

