package net.coljate.util.functions;

import java.util.function.Function;

import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 *
 * @author Ollie
 * @see Function
 */
public interface CheckedFunction<F, T, X extends Exception> {

    @CheckForNull
    T apply(@Nullable F from) throws X;

    @CheckReturnValue
    default <T2> CheckedFunction<F, T2, X> andThen(final Function<? super T, ? extends T2> after) {
        return object -> after.apply(this.apply(object));
    }

    @CheckReturnValue
    default <T2> CheckedFunction<F, T2, X> andThen(final CheckedFunction<? super T, ? extends T2, ? extends X> after) {
        return object -> after.apply(this.apply(object));
    }

    @CheckReturnValue
    @SuppressWarnings("unchecked") //This only throws checked exceptions of type X
    default Function<F, T> unchecked(final Function<? super X, ? extends RuntimeException> toRuntime) {
        return from -> {
            try {
                return this.apply(from);
            } catch (final RuntimeException rex) {
                throw rex;
            } catch (final Exception ex) { //Must be an X
                throw toRuntime.apply((X) ex);
            }
        };
    }

}
