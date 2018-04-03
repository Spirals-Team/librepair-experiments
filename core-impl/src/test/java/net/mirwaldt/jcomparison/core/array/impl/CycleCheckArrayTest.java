package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.decorator.tracing.CycleDetectingComparator;
import net.mirwaldt.jcomparison.core.basic.impl.ExceptionAtCycleComparator;
import net.mirwaldt.jcomparison.core.decorator.tracing.TracingComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.CycleDetectedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by Michael on 23.08.2017.
 */
public class CycleCheckArrayTest {

    ElementsComparator elementsComparator = new ElementsComparator();

    DefaultArrayComparator<Object> arrayComparator = DefaultComparators.createDefaultArrayComparatorBuilder().useElementsComparator(elementsComparator).build();
    ItemComparator<Object, ComparisonResult<?,?,?>> cycleProtectedArrayComparator = new CycleDetectingComparator(new TracingComparator(arrayComparator), new ExceptionAtCycleComparator());


    @BeforeEach
    public void setup() {
        elementsComparator.setArrayComparator(cycleProtectedArrayComparator);
        elementsComparator.setNonArrayComparator(cycleProtectedArrayComparator);
    }

    @Test
    public void test_directCycle_oneCycleLeft_cyclicWithItself() throws ComparisonFailedException {
        final Object[] leftObjectArray = new Object[1];
        final Object[] rightObjectArray = new Object[1];

        leftObjectArray[0] = leftObjectArray;
        rightObjectArray[0] = new Object();

        try {
            cycleProtectedArrayComparator.compare(leftObjectArray, rightObjectArray);
            fail("No cycle detected");
        } catch (CycleDetectedException e) {
            assertEquals(CycleDetectedException.CyclicTracesInfo.CyclePosition.LEFT_GRAPH, e.getCyclicTracesInfo().getCyclePosition());
        }
    }

    @Test
    public void test_directCycle_oneCycleRight_cyclicWithItself() throws ComparisonFailedException {
        final Object[] leftObjectArray = new Object[1];
        final Object[] rightObjectArray = new Object[1];

        leftObjectArray[0] = new Object();
        rightObjectArray[0] = rightObjectArray;

        try {
            cycleProtectedArrayComparator.compare(leftObjectArray, rightObjectArray);
            fail("No cycle detected");
        } catch (CycleDetectedException e) {
            assertEquals(CycleDetectedException.CyclicTracesInfo.CyclePosition.RIGHT_GRAPH, e.getCyclicTracesInfo().getCyclePosition());
        }
    }

    @Test
    public void test_directCycle_oneCycleEachLeftAndRight_cyclicWithThemselves() throws ComparisonFailedException {
        final Object[] leftObjectArray = new Object[1];
        final Object[] rightObjectArray = new Object[1];

        leftObjectArray[0] = leftObjectArray;
        rightObjectArray[0] = rightObjectArray;

        try {
            cycleProtectedArrayComparator.compare(leftObjectArray, rightObjectArray);
            fail("No cycle detected");
        } catch (CycleDetectedException e) {
            assertEquals(CycleDetectedException.CyclicTracesInfo.CyclePosition.BOTH_GRAPHS, e.getCyclicTracesInfo().getCyclePosition());
        }
    }

    @Test
    public void test_directCycle_oneCycleEachLeftAndRight_cyclicWithEachOther() throws ComparisonFailedException {
        final Object[] leftObjectArray = new Object[1];
        final Object[] rightObjectArray = new Object[1];

        leftObjectArray[0] = rightObjectArray;
        rightObjectArray[0] = leftObjectArray;

        try {
            cycleProtectedArrayComparator.compare(leftObjectArray, rightObjectArray);
            fail("No cycle detected");
        } catch (CycleDetectedException e) {
            assertEquals(CycleDetectedException.CyclicTracesInfo.CyclePosition.BOTH_GRAPHS, e.getCyclicTracesInfo().getCyclePosition());
        }
    }

    @Test
    public void test_indirectCycle_oneCycleLeft_cyclicWithOtherArray() throws ComparisonFailedException {
        final Object[][] subLeftObjectArray = new Object[1][];
        final Object[][] leftObjectArray = new Object[][]{{subLeftObjectArray}};
        subLeftObjectArray[0] = leftObjectArray[0];

        final Object[][] rightObjectArray = new Object[][]{{new Object[]{new Object()}}};

        try {
            cycleProtectedArrayComparator.compare(leftObjectArray, rightObjectArray);
            fail("No cycle detected");
        } catch (CycleDetectedException e) {
            assertEquals(CycleDetectedException.CyclicTracesInfo.CyclePosition.LEFT_GRAPH, e.getCyclicTracesInfo().getCyclePosition());
        }
    }

    @Test
    public void test_indirectCycle_oneCycleRight_cyclicWithItself() throws ComparisonFailedException {
        final Object[][] leftObjectArray = new Object[][]{{new Object[]{new Object()}}};

        final Object[][] subRightObjectArray = new Object[1][];
        final Object[][] rightObjectArray = new Object[][]{{subRightObjectArray}};
        subRightObjectArray[0] = rightObjectArray[0];

        try {
            cycleProtectedArrayComparator.compare(leftObjectArray, rightObjectArray);
            fail("No cycle detected");
        } catch (CycleDetectedException e) {
            assertEquals(CycleDetectedException.CyclicTracesInfo.CyclePosition.RIGHT_GRAPH, e.getCyclicTracesInfo().getCyclePosition());
        }
    }

    @Test
    public void test_indirectCycle_oneCycleEachLeftAndRight_cyclicWithThemselves() throws ComparisonFailedException {
        final Object[][] subLeftObjectArray = new Object[1][];
        final Object[][] leftObjectArray = new Object[][]{{subLeftObjectArray}};
        subLeftObjectArray[0] = leftObjectArray[0];

        final Object[][] subRightObjectArray = new Object[1][];
        final Object[][] rightObjectArray = new Object[][]{{subRightObjectArray}};
        subRightObjectArray[0] = rightObjectArray[0];

        try {
            cycleProtectedArrayComparator.compare(leftObjectArray, rightObjectArray);
            fail("No cycle detected");
        } catch (CycleDetectedException e) {
            assertEquals(CycleDetectedException.CyclicTracesInfo.CyclePosition.BOTH_GRAPHS, e.getCyclicTracesInfo().getCyclePosition());
        }
    }
}
