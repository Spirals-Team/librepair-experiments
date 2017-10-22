package spoon.test.generics.testclasses;

import java.util.Iterator;
import java.util.function.Consumer;

import spoon.reflect.code.CtConditional;

/**
 * Created by urli on 21/06/2017.
 */
public class SameSignature<T extends String> implements ISameSignature<T> {
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }
    
    @Override
	public <U> void visitCtConditional(final CtConditional<U> conditional) {
	}
}

interface ISameSignature<T> {
    Iterator<T> iterator();
    default void forEach(Consumer<? super T> action) {
    }
	<V> void visitCtConditional(final CtConditional<V> conditional);
}
