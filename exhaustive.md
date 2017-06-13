# Issue 125: Exhaustive/sampling modes, `@Only`/`@Also` parameter markers

## "Sampling" mode vs. "exhaustive" mode

* For `mode == SAMPLING`, there will be `trials` values for each parameter.
   * If the parameter is marked with `@Only`, then the values are chosen
   at random from the specified set.
   * If the parameter is marked with `@Also`, then those values are used,
   and `trials - size(also)` values are chosen from the generators.
   * Otherwise, the values are chosen exclusively by a generator. **done**
   * `@Only` wins over `@Also`.
   * There will be `trials` number of executions of the property, once for
   each "tuple" of arguments. **done**

* For `mode == EXHAUSTIVE`, there will be `trials` values for a parameter.
**done**
* However:
    * If the parameter has type `boolean`, `Boolean`, or an `enum`,
    there will be 2, 2, or `values().length` values, comprising the type's
    entire domain.
    * If the parameter is marked with `@Only`, then only all the values
    are used, and there will be `size(only)` values used.
    * If the parameter is marked with `@Also`, then those values are used,
    and `trials - size(also)` values are chosen from the generators.
    * Otherwise, `trials` values are chosen by the generator. **done**
    * `@Only` wins over `@Also`.
    * There will be `product[ cardinality(p) | p in parameters ]` number
    of executions of the property, one for each member of the cross-product
    of values to be used for each parameter. **done**

## Specifying values via `@Only/@Also`

    * Will a `String[] value()` attribute be enough?
        * If so, what about a `String`-to-X conversion strategy?
            * The usual one-arg `String` ctor vs. `valueOf()`?
        * If not - how else to specify the values?
            * `Supplier<List<X>>`? Need a way to know the size in advance.
