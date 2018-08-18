/*     / \____  _    ______   _____ / \____   ____  _____
 *    /  \__  \/ \  / \__  \ /  __//  \__  \ /    \/ __  \   Javaslang
 *  _/  // _\  \  \/  / _\  \\_  \/  // _\  \  /\  \__/  /   Copyright 2014-2015 Daniel Dietrich
 * /___/ \_____/\____/\_____/____/\___\_____/_/  \_/____/    Licensed under the Apache License, Version 2.0
 */
package javaslang;

/*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*\
   G E N E R A T O R   C R A F T E D
\*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class Function3Test {

    @Test
    public void shouldLift() {
        class Type {
            Object methodReference(Object o1, Object o2, Object o3) {
                return null;
            }
        }
        final Type type = new Type();
        assertThat(Function3.lift(type::methodReference)).isNotNull();
    }

    @Test
    public void shouldPartiallyApplyWith1Arguments() {
        final Function3<Object, Object, Object, Object> f = (o1, o2, o3) -> null;
        assertThat(f.apply(null)).isNotNull();
    }

    @Test
    public void shouldPartiallyApplyWith2Arguments() {
        final Function3<Object, Object, Object, Object> f = (o1, o2, o3) -> null;
        assertThat(f.apply(null, null)).isNotNull();
    }

    @Test
      public void shouldRecognizeApplicabilityOfNull() {
          final Function3<Object, Object, Object, Object> f = (o1, o2, o3) -> null;
          assertThat(f.isApplicableTo(null, null, null)).isTrue();
      }

      @Test
      public void shouldRecognizeApplicabilityOfNonNull() {
          final Function3<Integer, Integer, Integer, Integer> f = (i1, i2, i3) -> null;
          assertThat(f.isApplicableTo(1, 2, 3)).isTrue();
      }

      @Test
      public void shouldRecognizeApplicabilityToTypes() {
          final Function3<Integer, Integer, Integer, Integer> f = (i1, i2, i3) -> null;
          assertThat(f.isApplicableToTypes(Integer.class, Integer.class, Integer.class)).isTrue();
      }

    @Test
    public void shouldGetArity() {
        final Function3<Object, Object, Object, Object> f = (o1, o2, o3) -> null;
        assertThat(f.arity()).isEqualTo(3);
    }

    @Test
    public void shouldCurry() {
        final Function3<Object, Object, Object, Object> f = (o1, o2, o3) -> null;
        final Function1<Object, Function1<Object, Function1<Object, Object>>> curried = f.curried();
        assertThat(curried).isNotNull();
    }

    @Test
    public void shouldTuple() {
        final Function3<Object, Object, Object, Object> f = (o1, o2, o3) -> null;
        final Function1<Tuple3<Object, Object, Object>, Object> tupled = f.tupled();
        assertThat(tupled).isNotNull();
    }

    @Test
    public void shouldReverse() {
        final Function3<Object, Object, Object, Object> f = (o1, o2, o3) -> null;
        assertThat(f.reversed()).isNotNull();
    }

    @Test
    public void shouldMemoize() {
        final AtomicInteger integer = new AtomicInteger();
        final Function3<Integer, Integer, Integer, Integer> f = (i1, i2, i3) -> i1 + i2 + i3 + integer.getAndIncrement();
        final Function3<Integer, Integer, Integer, Integer> memo = f.memoized();
        // should apply f on first apply()
        final int expected = memo.apply(1, 2, 3);
        // should return memoized value of second apply()
        assertThat(memo.apply(1, 2, 3)).isEqualTo(expected);
        // should calculate new values when called subsequently with different parameters
        assertThat(memo.apply(2 , 3 , 4 )).isEqualTo(2  + 3  + 4  + 1);
        // should return memoized value of second apply() (for new value)
        assertThat(memo.apply(2 , 3 , 4 )).isEqualTo(2  + 3  + 4  + 1);
    }

    @Test
    public void shouldNotMemoizeAlreadyMemoizedFunction() {
        final Function3<Integer, Integer, Integer, Integer> f = (i1, i2, i3) -> null;
        final Function3<Integer, Integer, Integer, Integer> memo = f.memoized();
        assertThat(memo.memoized() == memo).isTrue();
    }

    @Test
    public void shouldMemoizeValueGivenNullArguments() {
        final Function3<Integer, Integer, Integer, Integer> f = (i1, i2, i3) -> null;
        final Function3<Integer, Integer, Integer, Integer> memo = f.memoized();
        assertThat(memo.apply(null, null, null)).isNull();
    }

    @Test
    public void shouldRecognizeMemoizedFunctions() {
        final Function3<Integer, Integer, Integer, Integer> f = (i1, i2, i3) -> null;
        final Function3<Integer, Integer, Integer, Integer> memo = f.memoized();
        assertThat(f.isMemoized()).isFalse();
        assertThat(memo.isMemoized()).isTrue();
    }

    @Test
    public void shouldComposeWithAndThen() {
        final Function3<Object, Object, Object, Object> f = (o1, o2, o3) -> null;
        final Function1<Object, Object> after = o -> null;
        final Function3<Object, Object, Object, Object> composed = f.andThen(after);
        assertThat(composed).isNotNull();
    }

    @Test
    public void shouldGetType() {
        final Function3<Integer, Integer, Integer, Integer> f = (i1, i2, i3) -> null;
        final Function3.Type<Integer, Integer, Integer, Integer> type = f.getType();
        assertThat(type.toString()).isEqualTo("(java.lang.Integer, java.lang.Integer, java.lang.Integer) -> java.lang.Integer");
    }
}