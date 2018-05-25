package spoon.reflect.code;


public interface CtNewArray<T> extends spoon.reflect.code.CtExpression<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DIMENSION)
    java.util.List<spoon.reflect.code.CtExpression<java.lang.Integer>> getDimensionExpressions();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DIMENSION)
    <C extends spoon.reflect.code.CtNewArray<T>> C setDimensionExpressions(java.util.List<spoon.reflect.code.CtExpression<java.lang.Integer>> dimensions);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DIMENSION)
    <C extends spoon.reflect.code.CtNewArray<T>> C addDimensionExpression(spoon.reflect.code.CtExpression<java.lang.Integer> dimension);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DIMENSION)
    boolean removeDimensionExpression(spoon.reflect.code.CtExpression<java.lang.Integer> dimension);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    java.util.List<spoon.reflect.code.CtExpression<?>> getElements();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <C extends spoon.reflect.code.CtNewArray<T>> C setElements(java.util.List<spoon.reflect.code.CtExpression<?>> expression);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <C extends spoon.reflect.code.CtNewArray<T>> C addElement(spoon.reflect.code.CtExpression<?> expression);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    boolean removeElement(spoon.reflect.code.CtExpression<?> expression);

    @java.lang.Override
    spoon.reflect.code.CtNewArray<T> clone();
}

