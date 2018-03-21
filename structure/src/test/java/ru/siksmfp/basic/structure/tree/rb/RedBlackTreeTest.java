package ru.siksmfp.basic.structure.tree.rb;

import org.junit.Assert;
import org.junit.Test;
import ru.siksmfp.basic.structure.api.TreeStructure;

/**
 * @author Artem Karnov @date 3/13/2018.
 * @email artem.karnov@t-systems.com
 */
public class RedBlackTreeTest {
    private Integer firstKey = 1;
    private String firstValue = "1";
    private Integer secondKey = 2;
    private String secondValue = "2";
    private Integer thirdKey = 3;
    private String thirdValue = "3";
    private Integer fourthKey = 4;
    private String fourthValue = "4";
    private Integer fifthKey = 5;
    private String fifthValue = "5";


    //
    //    1                 2
    //      2      ->    1    3
    //        3
    //
    @Test
    public void leftRotation() {
        TreeStructure<Integer, String> tree = new RedBlackTree<>();
        tree.add(firstKey, firstValue);
        tree.add(secondKey, secondValue);
        tree.add(thirdKey, thirdValue);

        Assert.assertEquals(3, tree.size());
        Assert.assertEquals(firstValue, tree.get(firstKey));
        Assert.assertEquals(secondValue, tree.get(secondKey));
        Assert.assertEquals(thirdValue, tree.get(thirdKey));
    }


    //
    //          4                3
    //       3       ->       2     4
    //     2
    //
    @Test
    public void rightRotation() {
        TreeStructure<Integer, String> tree = new RedBlackTree<>();
        tree.add(fourthKey, fourthValue);
        tree.add(thirdKey, thirdValue);
        tree.add(secondKey, secondValue);

        Assert.assertEquals(3, tree.size());
        Assert.assertEquals(fourthValue, tree.get(fourthKey));
        Assert.assertEquals(thirdValue, tree.get(thirdKey));
        Assert.assertEquals(secondValue, tree.get(secondKey));
    }

    @Test
    public void containsKey() {
        TreeStructure<Integer, String> tree = new RedBlackTree<>();
        tree.add(firstKey, firstValue);
        tree.add(secondKey, secondValue);
        tree.add(thirdKey, thirdValue);

        Assert.assertTrue(tree.contains(firstKey));
        Assert.assertTrue(tree.contains(secondKey));
        Assert.assertTrue(tree.contains(thirdKey));
        Assert.assertFalse(tree.contains(fourthKey));
    }

    @Test
    public void containsValue() {
        TreeStructure<Integer, String> tree = new RedBlackTree<>();
        tree.add(firstKey, firstValue);
        tree.add(secondKey, secondValue);
        tree.add(thirdKey, thirdValue);

        Assert.assertTrue(tree.containsValue(firstValue));
        Assert.assertTrue(tree.containsValue(secondValue));
        Assert.assertTrue(tree.containsValue(thirdValue));
        Assert.assertFalse(tree.containsValue(fourthValue));
    }
}