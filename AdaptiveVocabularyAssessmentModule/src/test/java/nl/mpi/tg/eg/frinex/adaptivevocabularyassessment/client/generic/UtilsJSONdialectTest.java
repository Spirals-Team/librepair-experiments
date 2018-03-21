/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author olhshk
 */
public class UtilsJSONdialectTest {

    public UtilsJSONdialectTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getKey method, of class UtilsJSONdialect.
     */
    @Test
    public void testGetKey() throws Exception {
        System.out.println("getKey");

        String jsonString = "{key1:{val1}}";
        String key = "key1";
        String expResult = "{val1}";
        String result = UtilsJSONdialect.getKey(jsonString, key);
        assertEquals(expResult, result);

        String jsonString1 = "{key11: {val11} , key2:{val2}}";
        String key1 = "key11";
        String expResult1 = "{val11}";
        String result1 = UtilsJSONdialect.getKey(jsonString1, key1);
        assertEquals(expResult1, result1);
        String result11 = UtilsJSONdialect.getKey(jsonString1, "key2");
        assertEquals("{val2}", result11);

        String jsonString2 = "{key21: {val21} , key22:{val22}, key23:{val23}  }";
        String key2 = "key22";
        String expResult2 = "{val22}";
        String result2 = UtilsJSONdialect.getKey(jsonString2, key2);
        assertEquals(expResult2, result2);

        String jsonString3 = "{key21: {val21} , key22:{val22,{},{{val221},{}}}, key23:{val23}  }";
        String key3 = "key22";
        String expResult3 = "{val22,{},{{val221},{}}}";
        String result3 = UtilsJSONdialect.getKey(jsonString3, key3);
        assertEquals(expResult3, result3);

    }

    /**
     * Test of arrayListToString method, of class UtilsJSONdialect.
     */
    @Test
    public void testArrayListToString() throws Exception {
        System.out.println("arrayListToString");
        UtilsJSONdialect<String> instance = new UtilsJSONdialect<String>();
        ArrayList<String> input = new ArrayList<String>();
        input.add("rhabarber");
        input.add("compot");
        input.add("{rhabarber,compot}");
        String expResult = "{0:{rhabarber},1:{compot},2:{rhabarber,compot}}";
        String result = instance.arrayListToString(input);
        assertEquals(expResult, result);
    }

    /**
     * Test of arrayListToString method, of class UtilsJSONdialect.
     */
    @Test
    public void testArrayListToString2() throws Exception {
        System.out.println("arrayListToString");
        UtilsJSONdialect<Integer> instance = new UtilsJSONdialect<Integer>();
        ArrayList<Integer> input = new ArrayList<Integer>();
        input.add(100);
        input.add(200);
        input.add(350);
        String expResult = "{0:{100},1:{200},2:{350}}";
        String result = instance.arrayListToString(input);
        assertEquals(expResult, result);
    }

    /**
     * Test of stringToArrayList method, of class UtilsJSONdialect.
     */
    @Test
    public void testStringToArrayList() throws Exception {
        System.out.println("stringToArrayList");
        String listStr = "{0:{rhabarber},1:{compot},2:{rhabarber,compot}}";
        ArrayList<String> expResult = new ArrayList<String>();
        expResult.add("{rhabarber}");
        expResult.add("{compot}");
        expResult.add("{rhabarber,compot}");
        UtilsJSONdialect instance = new UtilsJSONdialect();
        ArrayList<String> result = instance.stringToArrayList(listStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of stringToArrayList method, of class UtilsJSONdialect.
     */
    @Test
    public void testStringToArrayList2() throws Exception {
        System.out.println("stringToArrayList");
        String listStr = "{0:{1},1:{2},2:{3,4,5}}";
        ArrayList<String> expResult = new ArrayList<String>();
        expResult.add("{1}");
        expResult.add("{2}");
        expResult.add("{3,4,5}");
        UtilsJSONdialect instance = new UtilsJSONdialect();
        ArrayList<String> result = instance.stringToArrayList(listStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeFirstAndLast method, of class UtilsJSONdialect.
     */
    @Test
    public void testRemoveFirstAndLast() {
        System.out.println("removeFirstAndLast");
        String str = "{abc}";
        String expResult = "abc";
        String result = UtilsJSONdialect.removeFirstAndLast(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of getKeyWithoutBrackets method, of class UtilsJSONdialect.
     */
    @Test
    public void testGetKeyWithoutBrackets() throws Exception {
        System.out.println("getKeyWithoutBrackets");
        String jsonString = "x:{y},param:{value},param1:{value1,value2}";
        String key = "param";
        String expResult = "value";
        String result = UtilsJSONdialect.getKeyWithoutBrackets(jsonString, key);
        assertEquals(expResult, result);
    }

    /**
     * Test of arrayList2String method, of class UtilsJSONdialect.
     */
    @Test
    public void testArrayList2String() throws Exception {
        System.out.println("arrayList2String");
        int n=4;
        UtilsJSONdialect<Integer> instance = new UtilsJSONdialect<Integer>();
        ArrayList<ArrayList<Integer>> input = new ArrayList<ArrayList<Integer>>(n);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> current = new ArrayList<Integer>(i);
            input.add(current); // should be {0,1,..,i-1} -> i:{0:{0},1:{1},..,i-1:{i-1}}
            for (int j = 0; j < i; j++) {
                 current.add(j);
            }
        }
        String expResult = "{0:{},1:{0:{0}},2:{0:{0},1:{1}},3:{0:{0},1:{1},2:{2}}}";
        String result = instance.arrayList2String(input);
        assertEquals(expResult, result);
    }

    /**
     * Test of arrayList3String method, of class UtilsJSONdialect.
     */
    @Ignore
    @Test
    public void testArrayList3String() throws Exception {
        System.out.println("arrayList3String");
        int n=3;
        UtilsJSONdialect<Integer> instance = new UtilsJSONdialect<Integer>();
        ArrayList<ArrayList<ArrayList<Integer>>> input = new  ArrayList<ArrayList<ArrayList<Integer>>>(n);
       
        for (int i = 0; i < n; i++) {
            ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>(i);
            input.add(current); // 
            for (int j = 0; j < i; j++) {
                 ArrayList<Integer> currentInner = new ArrayList<Integer>(j); //should be {0,1,..,j-1} -> i:{0:{0},1:{1},..,j-1:{j-1}}
                   for (int k = 0; k < j; k++) { 
                      currentInner.add(k);
                   }
            }
        }
        String expResult = "{0:{},1:{0:{}},2:{0:{},1:{0:{0}}}}";
        String result = instance.arrayList3String(input);
        assertEquals(expResult, result);
    }

    /**
     * Test of intArrayListToString method, of class UtilsJSONdialect.
     */
    @Ignore
    @Test
    public void testIntArrayListToString() throws Exception {
        System.out.println("intArrayListToString");
        int[] arr = null;
        UtilsJSONdialect instance = new UtilsJSONdialect();
        String expResult = "";
        String result = instance.intArrayListToString(arr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doubleArrayListToString method, of class UtilsJSONdialect.
     */
    @Ignore
    @Test
    public void testDoubleArrayListToString() throws Exception {
        System.out.println("doubleArrayListToString");
        double[] arr = null;
        UtilsJSONdialect instance = new UtilsJSONdialect();
        String expResult = "";
        String result = instance.doubleArrayListToString(arr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stringToArrayListInteger method, of class UtilsJSONdialect.
     */
    @Ignore
    @Test
    public void testStringToArrayListInteger() throws Exception {
        System.out.println("stringToArrayListInteger");
        String listStr = "";
        UtilsJSONdialect instance = new UtilsJSONdialect();
        ArrayList<Integer> expResult = null;
        ArrayList<Integer> result = instance.stringToArrayListInteger(listStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stringToArrayDouble method, of class UtilsJSONdialect.
     */
    @Ignore
    @Test
    public void testStringToArrayDouble() throws Exception {
        System.out.println("stringToArrayDouble");
        String listStr = "{0:{0.5},1:{1.4},2:{2.3},3:{3.2}}";
        UtilsJSONdialect instance = new UtilsJSONdialect();
        double[] expResult = new double[4];
        for (int i = 0; i < 4; i++) {
            expResult[i] = i + (5 - i) / 10;
        }
        double[] result = instance.stringToArrayDouble(listStr);
        for (int i = 0; i < result.length; i++) {
            assertTrue(expResult[i] == result[i]);
        }
    }

}
