package spoon.test.annotation.testclasses;


@java.lang.annotation.Target({ java.lang.annotation.ElementType.FIELD })
public @interface BoundNumber {
    spoon.test.annotation.testclasses.BoundNumber.ByteOrder byteOrder = spoon.test.annotation.testclasses.BoundNumber.ByteOrder.LittleEndian;

    enum ByteOrder {
        LittleEndian;}
}

