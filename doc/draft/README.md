# Table of contents
## Architecture
## Design patterns
## Thorny issues

### Boxing/Unboxing of primitive values
Mutable primitives were introduced to avoid boxing/unboxing of primitive values.
Two mutable primitives are always used: One internally and one as view.
They are completely separate and thus independent so that accidentally changing the view has no effect.
This prevents very difficult bugs that are quite common when using mutable structures.
Of course, arrays with only one element could have been used but that would lead to special handling with array.
(When is it an array and when not because it actually is only a mutable container for the primitive value?)
Only a separate type (i.e. MutablePrimitive) can eliminate confusion.

The framework access fields/methods of objects via Java Reflection. This allows accessor patterns (e.g. "all fields with getter-methods") 
if the user wants to restrict the number of fields or methods that are considered for comparison.
Otherwise the user would need to write accessor objects/classes which is complicated and error-prone.
This would make the framework hard to use. However Java Reflection does not support primitive values very well.
It boxes values whenever you invoke methods via reflection or access fields via reflection.
I am thinking of way how to invoke methods with primitive return values and access fields of primitive values without boxing.

UPDATE 07.08.2017:
I discovered that Java Reflection has getters for the primitive fields so that boxing can be avoided.
However, you cannot invoke methods by reflection that return values of primitive types without boxing.

### Arrays.equals(...) and Array.deepEquals(...)
Arrays.equals(...) for arrays primitive is only defined for one dimensional arrays.
Arrays.deepEquals(...) only exists for multidimensional object arrays.
Casting an primitive array to an object array leads to heap pollution
because the primitive values are boxed and copied to a new object array.
This is unpleasant for big arrays. Therefore I prefer comparing (multidimensional) arrays rather by DefaultArrayComparator than with
with Arrays.equals(...) and Array.deepEquals(...). Moreover, an array IS an object and is very similar to a collection.
Considering arrays as one-value objects and comparing them by equals appears useless to me.

## Samples