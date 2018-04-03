package net.mirwaldt.jcomparison.primitive.collection.impl;

import net.mirwaldt.jcomparison.primitive.collection.api.NonBoxingList;
import net.mirwaldt.jcomparison.primitive.functionset.api.ObjectArrayListFunctionSet;
import net.mirwaldt.jcomparison.primitive.functionset.api.PrimitiveArrayListFunctionSet;
import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableSupplier;
import net.mirwaldt.jcomparison.primitive.mutable.impl.*;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;
import org.apache.commons.collections.primitives.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToIntBiFunction;
import java.util.stream.IntStream;

import static net.mirwaldt.jcomparison.primitive.mutable.api.Mutable.*;

public class BackedByArrayListsNonBoxingList implements NonBoxingList {
    private final MutableSupplier outputToMutableSupplier;

    private final ObjectArrayListFunctionSet objectArrayListFunctionSet;
    private final PrimitiveArrayListFunctionSet primitiveArrayListFunctionSet;
    
    private final VersionCounter versionCounter;
    private final ArrayIntList indexesOfObjects = new ArrayIntList(0);
    private final ArrayIntList indexesOfBooleans = new ArrayIntList(0);
    private final ArrayByteList bytes = new ArrayByteList(0);
    private final ArrayIntList indexesOfBytes = new ArrayIntList(0);
    private final ArrayCharList chars = new ArrayCharList(0);
    private final ArrayIntList indexesOfChars = new ArrayIntList(0);
    private final ArrayShortList shorts = new ArrayShortList(0);
    private final ArrayIntList indexesOfShorts = new ArrayIntList(0);
    private final ArrayIntList ints = new ArrayIntList(0);
    private final ArrayIntList indexesOfInts = new ArrayIntList(0);
    private final ArrayLongList longs = new ArrayLongList(0);
    private final ArrayIntList indexesOfLongs = new ArrayIntList(0);
    private final ArrayFloatList floats = new ArrayFloatList(0);
    private final ArrayIntList indexesOfFloats = new ArrayIntList(0);
    private final ArrayDoubleList doubles = new ArrayDoubleList(0);
    private final ArrayIntList indexesOfDoubles = new ArrayIntList(0);
    private final ArrayByteList typesOfElements = new ArrayByteList(0);
    private final ArrayIntList indexesOfElements = new ArrayIntList(0);
    private int falseCount = 0;
    private int trueCount = 0;
    private List<Object> objects = new ArrayList<>(0);

    public BackedByArrayListsNonBoxingList(MutableSupplier outputToMutableSupplier,
                                           ObjectArrayListFunctionSet objectArrayListFunctionSet,
                                           PrimitiveArrayListFunctionSet primitiveArrayListFunctionSet,
                                           VersionCounter versionCounter) {
        this.outputToMutableSupplier = outputToMutableSupplier;
        this.objectArrayListFunctionSet = objectArrayListFunctionSet;
        this.primitiveArrayListFunctionSet = primitiveArrayListFunctionSet;
        this.versionCounter = versionCounter;
    }

    @Override
    public int size() {
        if (indexesOfElements.isEmpty()) {
            return 0;
        } else {
            return indexesOfElements.size();
        }
    }

    @Override
    public Iterator<Mutable<?, ?>> iterator() {
        return new Iterator<Mutable<?, ?>>() {
            int version = versionCounter.getAsInt();
            int index = -1;

            @Override
            public boolean hasNext() {
                if (version == versionCounter.getAsInt()) {
                    throw new ConcurrentModificationException();
                }
                return index++ < size();
            }

            @Override
            public Mutable<?, ?> next() {
                if (version == versionCounter.getAsInt()) {
                    throw new ConcurrentModificationException();
                }
                return get(index);
            }

            @Override
            public void remove() {
                if (version == versionCounter.getAsInt()) {
                    throw new ConcurrentModificationException();
                }
                BackedByArrayListsNonBoxingList.this.remove(index);
                version = versionCounter.getAsInt();
            }
        };
    }

    @Override
    public boolean contains(Mutable<?, ?> element) {
        Objects.requireNonNull(element);

        return -1 < indexOf(element);
    }

    @Override
    public int add(Mutable<?, ?> element) {
        Objects.requireNonNull(element);

        int index = size();
        add(index, element);

        return index;
    }

    @Override
    public int remove(Mutable<?, ?> element) {
        Objects.requireNonNull(element);

        int index = indexOf(element);
        if (-1 < index) {
            remove(index);
        }

        return index;
    }

    @Override
    public void clear() {
        objects.clear();
        indexesOfObjects.clear();

        bytes.clear();
        indexesOfBytes.clear();

        chars.clear();
        indexesOfChars.clear();

        shorts.clear();
        indexesOfShorts.clear();

        ints.clear();
        indexesOfInts.clear();

        longs.clear();
        indexesOfLongs.clear();

        floats.clear();
        indexesOfFloats.clear();

        doubles.clear();
        indexesOfDoubles.clear();

        typesOfElements.clear();
        indexesOfElements.clear();

        versionCounter.increment();
    }

    @Override
    public Mutable<?, ?> get(int index) {
        return get(index, outputToMutableSupplier);
    }

    @Override
    public Mutable<?, ?> get(int index, MutableSupplier returnedSupplier) {
        checkBounds(index);

        versionCounter.increment();

        final int typeOfElement = typesOfElements.get(index);
        final int indexOfElement = indexesOfElements.get(index);
        final Mutable<?, ?> mutable = returnedSupplier.getOrCreateMutable(typeOfElement);
        if (typeOfElement == TYPE_OBJECT) {
            final DefaultMutableObject mutableObject = (DefaultMutableObject) mutable;
            mutableObject.setObject(objects.get(indexOfElement));
            return mutableObject;
        } else if (typeOfElement == TYPE_BOOLEAN) {
            final DefaultMutableBoolean mutableBoolean = (DefaultMutableBoolean) mutable;
            if (indexOfElement < falseCount) {
                mutableBoolean.setValue(false);
            } else {
                mutableBoolean.setValue(true);
            }
            return mutableBoolean;
        } else if (typeOfElement == TYPE_BYTE) {
            final DefaultMutableByte mutableByte = (DefaultMutableByte) mutable;
            mutableByte.setValue(bytes.get(indexOfElement));
            return mutableByte;
        } else if (typeOfElement == TYPE_CHAR) {
            final DefaultMutableChar mutableChar = (DefaultMutableChar) mutable;
            mutableChar.setValue(chars.get(indexOfElement));
            return mutableChar;
        } else if (typeOfElement == TYPE_SHORT) {
            final DefaultMutableShort mutableShort = (DefaultMutableShort) mutable;
            mutableShort.setValue(shorts.get(indexOfElement));
            return mutableShort;
        } else if (typeOfElement == TYPE_INT) {
            final DefaultMutableInt mutableInt = (DefaultMutableInt) mutable;
            mutableInt.setValue(ints.get(indexOfElement));
            return mutableInt;
        } else if (typeOfElement == TYPE_LONG) {
            final DefaultMutableLong defaultMutableLong = (DefaultMutableLong) mutable;
            defaultMutableLong.setValue(longs.get(indexOfElement));
            return defaultMutableLong;
        } else if (typeOfElement == TYPE_FLOAT) {
            final DefaultMutableFloat defaultMutableFloat = (DefaultMutableFloat) mutable;
            defaultMutableFloat.setValue(floats.get(indexOfElement));
            return defaultMutableFloat;
        } else if (typeOfElement == TYPE_DOUBLE) {
            final DefaultMutableDouble mutableDouble = (DefaultMutableDouble) mutable;
            mutableDouble.setValue(doubles.get(indexOfElement));
            return mutableDouble;
        } else {
            throw new IllegalStateException("Unknown type '" + typeOfElement + "'.");
        }
    }

    private void checkBounds(int index) {
        if (!(-1 < index && index < size())) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkBoundsInclusive(int index) {
        if (!(-1 < index && index <= size())) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Mutable<?, ?> set(int index, Mutable<?, ?> element) {
        return set(index, element, outputToMutableSupplier);
    }

    @Override
    public Mutable<?, ?> set(int index, Mutable<?, ?> element, MutableSupplier returnedSupplier) {
        checkBounds(index);
        Objects.requireNonNull(element);

        // TODO: update listIndexes must not happen (indexes do not change) => disable it here
        Mutable<?, ?> previous = remove(index, returnedSupplier);
        add(index, element, returnedSupplier);

        return previous;
    }

    @Override
    public Mutable<?, ?> add(int index, Mutable<?, ?> element) {
        return add(index, element, outputToMutableSupplier);
    }

    @Override
    public Mutable<?, ?> add(int index, Mutable<?, ?> element, MutableSupplier returnedSupplier) {
        checkBoundsInclusive(index);
        Objects.requireNonNull(element);

        versionCounter.increment();

        final int indexInList = index;
        final Mutable<?, ?> previousElement = get(index, returnedSupplier);
        if (element instanceof DefaultMutableBoolean) {
            final DefaultMutableBoolean mutableBoolean = (DefaultMutableBoolean) element;
            final boolean value = mutableBoolean.getValue();
            final int indexInBooleans;
            if (value) {
                trueCount++;
                indexInBooleans = falseCount + trueCount - 1;
            } else {
                falseCount++;
                indexInBooleans = falseCount - 1;
            }

            indexesOfBooleans.add(indexInBooleans, indexInList);
            typesOfElements.add(indexInList, TYPE_BOOLEAN);
            indexesOfElements.add(indexInList, indexInBooleans);
        } else if (element instanceof DefaultMutableByte) {
            final int indexInBytes = addPrimitiveFunction().applyAsInt(element, indexesOfBytes);

            indexesOfBytes.add(indexInBytes, indexInList);
            typesOfElements.add(indexInList, TYPE_BYTE);
            indexesOfElements.add(indexInList, indexInBytes);
        } else if (element instanceof DefaultMutableChar) {
            final int indexInChars = addPrimitiveFunction().applyAsInt(element, indexesOfChars);

            indexesOfChars.add(indexInChars, indexInList);
            typesOfElements.add(indexInList, TYPE_CHAR);
            indexesOfElements.add(indexInList, indexInChars);
        } else if (element instanceof DefaultMutableShort) {
            final int indexInShorts = addPrimitiveFunction().applyAsInt(element, indexesOfShorts);

            indexesOfShorts.add(indexInShorts, indexInList);
            typesOfElements.add(indexInList, TYPE_SHORT);
            indexesOfElements.add(indexInList, indexInShorts);
        } else if (element instanceof DefaultMutableInt) {
            final int indexInInts = addPrimitiveFunction().applyAsInt(element, indexesOfInts);

            indexesOfInts.add(indexInInts, indexInList);
            typesOfElements.add(indexInList, TYPE_INT);
            indexesOfElements.add(indexInList, indexInInts);
        } else if (element instanceof DefaultMutableLong) {
            final int indexInLongs = addPrimitiveFunction().applyAsInt(element, indexesOfLongs);

            indexesOfLongs.add(indexInLongs, indexInList);
            typesOfElements.add(indexInList, TYPE_LONG);
            indexesOfElements.add(indexInList, indexInLongs);
        } else if (element instanceof DefaultMutableFloat) {
            final int indexInFloats = addPrimitiveFunction().applyAsInt(element, indexesOfFloats);

            indexesOfFloats.add(indexInFloats, indexInList);
            typesOfElements.add(indexInList, TYPE_FLOAT);
            indexesOfElements.add(indexInList, indexInFloats);
        } else if (element instanceof DefaultMutableDouble) {
            final int indexInDoubles = addPrimitiveFunction().applyAsInt(element, indexesOfDoubles);

            indexesOfDoubles.add(indexInDoubles, indexInList);
            typesOfElements.add(indexInList, TYPE_DOUBLE);
            indexesOfElements.add(indexInList, indexInDoubles);
        } else {
            final Object object = element;
            final int indexInObjects = addObjectFunction().applyAsInt(object, objects);

            indexesOfObjects.add(indexInObjects, indexInList);
            typesOfElements.add(indexInList, TYPE_OBJECT);
            indexesOfElements.add(indexInList, indexInObjects);
        }
        updateListIndexes(index + 1, +1);

        return previousElement;
    }

    private void updateListIndexes(int startIndex, int summand) {
        for (int index = startIndex; index < size(); index++) {
            final int typeOfElement = typesOfElements.get(index);
            final int indexOfElement = indexesOfElements.get(index);
            if (typeOfElement == TYPE_OBJECT) {
                final int newIndex = indexesOfObjects.get(indexOfElement) + summand;
                indexesOfObjects.set(indexOfElement, newIndex);
            } else if (typeOfElement == TYPE_BOOLEAN) {
                final int newIndex = indexesOfBooleans.get(indexOfElement) + summand;
                indexesOfBooleans.set(indexOfElement, newIndex);
            } else if (typeOfElement == TYPE_BYTE) {
                final int newIndex = indexesOfBytes.get(indexOfElement) + summand;
                indexesOfBytes.set(indexOfElement, newIndex);
            } else if (typeOfElement == TYPE_CHAR) {
                final int newIndex = indexesOfChars.get(indexOfElement) + summand;
                indexesOfChars.set(indexOfElement, newIndex);
            } else if (typeOfElement == TYPE_SHORT) {
                final int newIndex = indexesOfShorts.get(indexOfElement) + summand;
                indexesOfShorts.set(indexOfElement, newIndex);
            } else if (typeOfElement == TYPE_INT) {
                final int newIndex = indexesOfInts.get(indexOfElement) + summand;
                indexesOfInts.set(indexOfElement, newIndex);
            } else if (typeOfElement == TYPE_LONG) {
                final int newIndex = indexesOfLongs.get(indexOfElement) + summand;
                indexesOfLongs.set(indexOfElement, newIndex);
            } else if (typeOfElement == TYPE_FLOAT) {
                final int newIndex = indexesOfFloats.get(indexOfElement) + summand;
                indexesOfFloats.set(indexOfElement, newIndex);
            } else if (typeOfElement == TYPE_DOUBLE) {
                final int newIndex = indexesOfDoubles.get(indexOfElement) + summand;
                indexesOfDoubles.set(indexOfElement, newIndex);
            } else {
                throw new IllegalStateException("Unknown type '" + typeOfElement + "'.");
            }
        }
    }

    @Override
    public Mutable<?, ?> remove(int index) {
        return remove(index, outputToMutableSupplier);
    }

    @Override
    public Mutable<?, ?> remove(int index, MutableSupplier returnedSupplier) {
        checkBounds(index);

        versionCounter.increment();

        final Mutable<?, ?> previous = get(index, returnedSupplier);

        final int typeOfElement = typesOfElements.removeElementAt(index);
        final int indexOfElement = indexesOfElements.removeElementAt(index);
        if (typeOfElement == TYPE_OBJECT) {
            objects.remove(indexOfElement);
            indexesOfObjects.removeElementAt(indexOfElement);
        } else if (typeOfElement == TYPE_BOOLEAN) {
            DefaultMutableBoolean mutableBoolean = (DefaultMutableBoolean) previous;
            if (mutableBoolean.getValue()) {
                trueCount--;
            } else {
                falseCount--;
            }
            indexesOfBooleans.removeElementAt(indexOfElement);
        } else if (typeOfElement == TYPE_BYTE) {
            objects.remove(indexOfElement);
            indexesOfObjects.removeElementAt(indexOfElement);
        } else if (typeOfElement == TYPE_CHAR) {
            objects.remove(indexOfElement);
            indexesOfObjects.removeElementAt(indexOfElement);
        } else if (typeOfElement == TYPE_SHORT) {
            objects.remove(indexOfElement);
            indexesOfObjects.removeElementAt(indexOfElement);
        } else if (typeOfElement == TYPE_INT) {
            objects.remove(indexOfElement);
            indexesOfObjects.removeElementAt(indexOfElement);
        } else if (typeOfElement == TYPE_LONG) {
            objects.remove(indexOfElement);
            indexesOfObjects.removeElementAt(indexOfElement);
        } else if (typeOfElement == TYPE_FLOAT) {
            objects.remove(indexOfElement);
            indexesOfObjects.removeElementAt(indexOfElement);
        } else if (typeOfElement == TYPE_DOUBLE) {
            objects.remove(indexOfElement);
            indexesOfObjects.removeElementAt(indexOfElement);
        } else {
            throw new IllegalStateException("Unknown type '" + typeOfElement + "'.");
        }

        updateListIndexes(index, -1);

        return previous;
    }

    @Override
    public int indexOf(Mutable<?, ?> element) {
        Objects.requireNonNull(element);

        if (element instanceof DefaultMutableBoolean) {
            final DefaultMutableBoolean mutableBoolean = (DefaultMutableBoolean) element;
            final boolean value = mutableBoolean.getValue();
            final int valueIndex;
            if (value) {
                if (0 < trueCount) {
                    if (0 < falseCount) {
                        valueIndex = falseCount;
                    } else {
                        valueIndex = 0;
                    }
                } else {
                    valueIndex = -1;
                }
            } else {
                if (0 < falseCount) {
                    valueIndex = 0;
                } else {
                    valueIndex = -1;
                }
            }

            if (-1 < valueIndex) {
                return indexesOfBooleans.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableByte) {
            final int valueIndex = indexOfPrimitiveFunction().applyAsInt(element, bytes);
            if (-1 < valueIndex) {
                return indexesOfBytes.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableChar) {
            final int valueIndex = indexOfPrimitiveFunction().applyAsInt(element, chars);
            if (-1 < valueIndex) {
                return indexesOfChars.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableShort) {
            final int valueIndex = indexOfPrimitiveFunction().applyAsInt(element, shorts);
            if (-1 < valueIndex) {
                return indexesOfShorts.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableInt) {
            final int valueIndex = indexOfPrimitiveFunction().applyAsInt(element, ints);
            if (-1 < valueIndex) {
                return indexesOfInts.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableLong) {
            final int valueIndex = indexOfPrimitiveFunction().applyAsInt(element, longs);
            if (-1 < valueIndex) {
                return indexesOfLongs.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableFloat) {
            final int valueIndex = indexOfPrimitiveFunction().applyAsInt(element, floats);
            if (-1 < valueIndex) {
                return indexesOfFloats.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableDouble) {
            final int valueIndex = indexOfPrimitiveFunction().applyAsInt(element, doubles);
            if (-1 < valueIndex) {
                return indexesOfDoubles.get(valueIndex);
            } else {
                return -1;
            }
        } else {
            final Object object = element;
            final int objectIndex = indexOfObjectFunction().applyAsInt(object, objects);
            if (-1 < objectIndex) {
                return indexesOfObjects.get(objectIndex);
            } else {
                return -1;
            }
        }
    }

    @Override
    public int lastIndexOf(Mutable<?, ?> element) {
        Objects.requireNonNull(element);

        if (element instanceof DefaultMutableBoolean) {
            final DefaultMutableBoolean mutableBoolean = (DefaultMutableBoolean) element;
            final boolean value = mutableBoolean.getValue();
            final int valueIndex;
            if (value) {
                if (0 < trueCount) {
                    if (0 < falseCount) {
                        valueIndex = falseCount + trueCount - 1;
                    } else {
                        valueIndex = trueCount - 1;
                    }
                } else {
                    valueIndex = -1;
                }
            } else {
                if (0 < falseCount) {
                    valueIndex = falseCount - 1;
                } else {
                    valueIndex = -1;
                }
            }

            if (-1 < valueIndex) {
                return indexesOfBooleans.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableByte) {
            final int valueIndex = lastIndexOfPrimitiveFunction().applyAsInt(element, bytes);
            if (-1 < valueIndex) {
                return indexesOfBytes.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableChar) {
            final int valueIndex = lastIndexOfPrimitiveFunction().applyAsInt(element, chars);
            if (-1 < valueIndex) {
                return indexesOfChars.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableShort) {
            final int valueIndex = lastIndexOfPrimitiveFunction().applyAsInt(element, shorts);
            if (-1 < valueIndex) {
                return indexesOfShorts.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableInt) {
            final int valueIndex = lastIndexOfPrimitiveFunction().applyAsInt(element, ints);
            if (-1 < valueIndex) {
                return indexesOfInts.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableLong) {
            final int valueIndex = lastIndexOfPrimitiveFunction().applyAsInt(element, longs);
            if (-1 < valueIndex) {
                return indexesOfLongs.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableFloat) {
            final int valueIndex = lastIndexOfPrimitiveFunction().applyAsInt(element, floats);
            if (-1 < valueIndex) {
                return indexesOfFloats.get(valueIndex);
            } else {
                return -1;
            }
        } else if (element instanceof DefaultMutableDouble) {
            final int valueIndex = lastIndexOfPrimitiveFunction().applyAsInt(element, doubles);
            if (-1 < valueIndex) {
                return indexesOfDoubles.get(valueIndex);
            } else {
                return -1;
            }
        } else {
            final Object object = element;
            final int objectIndex = lastIndexOfObjectFunction().applyAsInt(object, objects);
            if (-1 < objectIndex) {
                return indexesOfObjects.get(objectIndex);
            } else {
                return -1;
            }
        }
    }

    @Override
    public void trimToSize() {
        final List<Object> newObjects = new ArrayList<>(objects.size());
        newObjects.addAll(objects);
        objects = newObjects;
        indexesOfObjects.trimToSize();

        bytes.trimToSize();
        indexesOfBytes.trimToSize();

        chars.trimToSize();
        indexesOfChars.trimToSize();

        shorts.trimToSize();
        indexesOfShorts.trimToSize();

        ints.trimToSize();
        indexesOfInts.trimToSize();

        longs.trimToSize();
        indexesOfLongs.trimToSize();

        floats.trimToSize();
        indexesOfFloats.trimToSize();

        doubles.trimToSize();
        indexesOfDoubles.trimToSize();

        typesOfElements.trimToSize();
        indexesOfElements.trimToSize();
    }

    public ToIntBiFunction<Object, List<Object>> indexOfObjectFunction() {
        return objectArrayListFunctionSet.indexOfObjectFunction();
    }

    public ToIntBiFunction<Object, List<Object>> lastIndexOfObjectFunction() {
        return objectArrayListFunctionSet.lastIndexOfObjectFunction();
    }

    public ToIntBiFunction<Object, List<Object>> addObjectFunction() {
        return objectArrayListFunctionSet.addObjectFunction();
    }

    public ToIntBiFunction<Mutable<?, ?>, Object> indexOfPrimitiveFunction() {
        return primitiveArrayListFunctionSet.indexOfPrimitiveFunction();
    }

    public ToIntBiFunction<Mutable<?, ?>, Object> lastIndexOfPrimitiveFunction() {
        return primitiveArrayListFunctionSet.lastIndexOfPrimitiveFunction();
    }

    public ToIntBiFunction<Mutable<?, ?>, Object> addPrimitiveFunction() {
        return primitiveArrayListFunctionSet.addPrimitiveFunction();
    }
}
