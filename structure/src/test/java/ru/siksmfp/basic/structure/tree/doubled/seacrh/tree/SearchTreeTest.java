package ru.siksmfp.basic.structure.tree.doubled.seacrh.tree;

import org.junit.Assert;
import org.junit.Test;
import ru.siksmfp.basic.structure.api.TreeStructure;

import java.util.Random;

/**
 * @author Artem Karnov @date 2/21/2018.
 * @email artem.karnov@t-systems.com
 */
public class SearchTreeTest {
    private static final int FIRST_KEY = 1;
    private static final int SECOND_KEY = 2;
    private static final int THIRD_KEY = 3;
    private static final int FOURTH_KEY = 4;
    private static final double INFELICITY = 0.001;
    private static final String FIRST_VALUE = "1";
    private static final String SECOND_VALUE = "2";
    private static final String THIRD_VALUE = "3";
    private static final String FOURTH_VALUE = "4";

    class InnerMutableClass {
        String stringField;
        Double doubleField;

        public InnerMutableClass(String stringField, Double doubleField) {
            this.stringField = stringField;
            this.doubleField = doubleField;
        }

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.stringField = stringField;
        }

        public Double getDoubleField() {
            return doubleField;
        }

        public void setDoubleField(Double doubleField) {
            this.doubleField = doubleField;
        }

        @Override
        public String toString() {
            return "InnerMutableClass{" +
                    "stringField='" + stringField + '\'' +
                    ", doubleField=" + doubleField +
                    '}';
        }
    }

    @Test
    public void add() {
        TreeStructure<Integer, String> tree = new SearchTree<>();

        tree.add(FIRST_KEY, FIRST_VALUE);
        tree.add(SECOND_KEY, SECOND_VALUE);
        tree.add(THIRD_KEY, THIRD_VALUE);

        Assert.assertNotNull(FIRST_VALUE, tree.get(FIRST_KEY));
        Assert.assertNotNull(SECOND_VALUE, tree.get(SECOND_KEY));
        Assert.assertNotNull(THIRD_VALUE, tree.get(THIRD_KEY));
    }

    @Test
    public void contains() {
        TreeStructure<Integer, String> tree = new SearchTree<>();

        tree.add(FIRST_KEY, FIRST_VALUE);
        tree.add(SECOND_KEY, SECOND_VALUE);
        tree.add(THIRD_KEY, THIRD_VALUE);

        Assert.assertEquals(3, tree.size());
        Assert.assertTrue(tree.contains(FIRST_KEY));
        Assert.assertTrue(tree.contains(SECOND_KEY));
        Assert.assertTrue(tree.contains(THIRD_KEY));
        Assert.assertFalse(tree.contains(FOURTH_KEY));
    }

    @Test
    public void containsValue() {
        TreeStructure<Integer, String> tree = new SearchTree<>();

        tree.add(FIRST_KEY, FIRST_VALUE);
        tree.add(SECOND_KEY, SECOND_VALUE);
        tree.add(THIRD_KEY, THIRD_VALUE);

        Assert.assertEquals(3, tree.size());
        Assert.assertTrue(tree.containsValue(FIRST_VALUE));
        Assert.assertTrue(tree.containsValue(SECOND_VALUE));
        Assert.assertTrue(tree.containsValue(THIRD_VALUE));
        Assert.assertFalse(tree.containsValue(FOURTH_VALUE));
    }

    @Test
    public void removeByKey() {
        TreeStructure<Integer, String> tree = new SearchTree<>();

        tree.add(FIRST_KEY, FIRST_VALUE);
        tree.add(SECOND_KEY, SECOND_VALUE);
        tree.add(THIRD_KEY, THIRD_VALUE);

        Assert.assertEquals(3, tree.size());

        tree.remove(THIRD_KEY);

        Assert.assertEquals(2, tree.size());
        Assert.assertTrue(tree.containsValue(FIRST_VALUE));
        Assert.assertTrue(tree.containsValue(SECOND_VALUE));
        Assert.assertFalse(tree.containsValue(THIRD_VALUE));
        Assert.assertFalse(tree.containsValue(FOURTH_VALUE));
    }

    @Test
    public void removeAllElementsAscending() {
        TreeStructure<Integer, String> tree = new SearchTree<>();
        int treeSize = 5;
        for (int i = 0; i < treeSize; i++) {
            tree.add(i, String.valueOf(i));
        }

        Random random = new Random();
        int actualSize = treeSize;
        for (int i = 0; i < treeSize; i++) {
            int randomPosition = random.nextInt(treeSize + 1) - 1;
            if (tree.containsValue(String.valueOf(randomPosition))) {
                actualSize--;
            }
            tree.remove(randomPosition);
            Assert.assertEquals(actualSize, tree.size());
            Assert.assertFalse(tree.containsValue(String.valueOf(randomPosition)));
        }

    }

    @Test
    public void removeFirst() {
        TreeStructure<Integer, String> tree = new SearchTree<>();

        tree.add(FIRST_KEY, FIRST_VALUE);
        tree.add(SECOND_KEY, SECOND_VALUE);

        tree.remove(SECOND_KEY);

        Assert.assertEquals(1, tree.size());
        Assert.assertTrue(tree.containsValue(FIRST_VALUE));

        tree.remove(FIRST_KEY);

        Assert.assertEquals(0, tree.size());


    }

    @Test
    public void removeValue() {
        TreeStructure<Integer, String> tree = new SearchTree<>();

        tree.add(FIRST_KEY, FIRST_VALUE);
        tree.add(SECOND_KEY, SECOND_VALUE);
        tree.add(THIRD_KEY, THIRD_VALUE);

        tree.removeValue(SECOND_VALUE);

        Assert.assertEquals(2, tree.size());
        Assert.assertTrue(tree.containsValue(FIRST_VALUE));
        Assert.assertFalse(tree.containsValue(SECOND_VALUE));
        Assert.assertTrue(tree.containsValue(THIRD_VALUE));
    }

    @Test
    public void strictAdd() {
        TreeStructure<Integer, InnerMutableClass> tree = new SearchTree<>();

        String stringValue1 = "Value1";
        String stringValue2 = "Value2";
        String stringValue3 = "Value3";
        Double doubleValue1 = 1.0;
        Double doubleValue2 = 2.0;
        Double doubleValue3 = 3.0;

        InnerMutableClass innerMutableClass1 = new InnerMutableClass(stringValue1, doubleValue1);
        InnerMutableClass innerMutableClass2 = new InnerMutableClass(stringValue2, doubleValue2);

        tree.add(FIRST_KEY, innerMutableClass1);
        tree.strictAdd(SECOND_KEY, innerMutableClass2);

        innerMutableClass1.setStringField(stringValue3);
        innerMutableClass1.setDoubleField(doubleValue3);

        innerMutableClass2.setStringField(stringValue3);
        innerMutableClass2.setDoubleField(doubleValue3);

        Assert.assertEquals(2, tree.size());
        Assert.assertEquals(stringValue3, tree.get(FIRST_KEY).getStringField());
        Assert.assertEquals(doubleValue3, tree.get(FIRST_KEY).getDoubleField(), INFELICITY);
        Assert.assertEquals(stringValue2, tree.get(SECOND_KEY).getStringField());
        Assert.assertEquals(doubleValue2, tree.get(SECOND_KEY).getDoubleField(), INFELICITY);
    }
}