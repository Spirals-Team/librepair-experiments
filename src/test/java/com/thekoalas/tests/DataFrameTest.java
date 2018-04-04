/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thekoalas.tests;

import com.thekoalas.koalas.Column;
import com.thekoalas.koalas.ColumnsNotSameSizeException;
import com.thekoalas.koalas.DataFrame;
import com.thekoalas.koalas.NameAlreadyDefinedException;
import com.thekoalas.koalas.NoColumnsException;
import com.thekoalas.koalas.NotAsMuchNamesAsColumnsException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author medardt
 */
public class DataFrameTest {
    
    private DataFrame defaultDataFrame;
    
    public DataFrameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ArrayList<Column> list = new ArrayList<Column>();
        ArrayList<Integer> col1IntList = new ArrayList<Integer>();
        
        col1IntList.add(2);
        col1IntList.add(1);
        
        ArrayList<String> col2StringList = new ArrayList<String>();
        col2StringList.add("Row1");
        col2StringList.add("Row2");
        list.add(new Column("A", col1IntList));
        list.add(new Column("B", col2StringList));
        
        defaultDataFrame = new DataFrame(list);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testHashCode() {
        
        int expected = 3;
        assertEquals(expected, defaultDataFrame.hashCode());
    }
    
    @Test
    public void testToString() {
        
        String expected = defaultDataFrame.display();
        assertEquals(expected.replaceAll("\\s+", ""), defaultDataFrame.toString().replaceAll("\\s+", ""));
    }
    
    @Test
    public void testGetDataset(){
        ArrayList<Column> list = new ArrayList<>();
        ArrayList<Integer> col1IntList = new ArrayList<>();
        col1IntList.add(2);
        col1IntList.add(1);
        ArrayList<String> col2StringList = new ArrayList<>();
        col2StringList.add("Row1");
        col2StringList.add("Row2");
        
        Column colA = new Column("A", col1IntList);
        Column colB = new Column("B", col2StringList);
        list.add(colA);
        list.add(colB);
        
        DataFrame data = new DataFrame(list);
        
        List<Column> expectedColumns = new ArrayList<Column>();
        expectedColumns.add(colA);
        expectedColumns.add(colB);
        
        List<Column> dataSetGet = data.getDataset();
        
        assertEquals(expectedColumns.size(), dataSetGet.size());
        for(int i = 0; i < dataSetGet.size(); ++i) {
            
            Column expectedCol = expectedColumns.get(i);
            Column getColumn   = dataSetGet.get(i);
            assertEquals(expectedCol, getColumn);
        }
    }
    
    @Test (expected = NoColumnsException.class)
    public void testGetColumnSetEmpty() {
        
        List<String> subset = new ArrayList<String>();
        defaultDataFrame.getColumnSubset(subset);
    }
    
    @Test (expected = NoColumnsException.class)
    public void testGetColumnSetInvalid() {
        
        List<String> subset = new ArrayList<String>();
        subset.add("Koalas!!!!!");
        defaultDataFrame.getColumnSubset(subset);
    }
    
    @Test
    public void testGetColumnSubset() {
        
        List<String> subset = new ArrayList<String>();
        subset.add("A");
        
        ArrayList<Column> list = new ArrayList<>();
        ArrayList<Integer> col1IntList = new ArrayList<>();
        col1IntList.add(1);
        col1IntList.add(2);
        ArrayList<String> col2StringList = new ArrayList<>();
        col2StringList.add("Row1");
        col2StringList.add("Row2");
        
        Column colA = new Column("A", col1IntList);
        Column colB = new Column("B", col2StringList);
        list.add(colA);
        list.add(colB);
        
        DataFrame data = new DataFrame(list);
        
        List<Column> expectedColumns = new ArrayList<Column>();
        expectedColumns.add(colA);
        
        DataFrame newData = data.getColumnSubset(subset);
        List<Column> dataSetGet = newData.getDataset();
        
        assertEquals(expectedColumns.size(), dataSetGet.size());
        for(int i = 0; i < dataSetGet.size(); ++i) {
            
            Column expectedCol = expectedColumns.get(i);
            Column getColumn   = dataSetGet.get(i);
            assertEquals(expectedCol, getColumn);
        }
    }
    
    @Test
    public void testStatistics() {
        
        String colBRow1 = "Row1";
        String colBRow2 = "Row2";
        
        String expectedA = "Colonne A : minimum is 1 maximum is 2 sum is 3.0 mean is 1.5\n";
        String expectedB = "Colonne B : minimum is " + colBRow1 + " maximum is " + colBRow2 + " sum is " + (colBRow1 + colBRow2) + " mean is (invlid type for mean)\n";
        
        String expected = expectedA + expectedB;
        
        assertEquals(expected, defaultDataFrame.statistics());
    }
    
    @Test
    public void testEquals() {
        
        assertTrue(defaultDataFrame.equals(defaultDataFrame));
        assertFalse(defaultDataFrame.equals(null));
        
        assertFalse(defaultDataFrame.equals("Koalas!!!!!!!!"));
        
        DataFrame test = new DataFrame(defaultDataFrame.getDataset());
        
        assertTrue(defaultDataFrame.equals(test));
        
        ArrayList<Column> list = new ArrayList<Column>();
        ArrayList<Integer> col1IntList = new ArrayList<Integer>();
        
        col1IntList.add(2);
        col1IntList.add(1);
        
        ArrayList<String> col2StringList = new ArrayList<String>();
        col2StringList.add("Row1");
        col2StringList.add("Row2");
        list.add(new Column("A", col1IntList));
        list.add(new Column("B", col2StringList));
        test = new DataFrame(list);
        
        assertTrue(defaultDataFrame.equals(test));
        
        list = new ArrayList<Column>();
        col1IntList = new ArrayList<Integer>();
        
        col1IntList.add(2);
        col1IntList.add(1);
        
        col2StringList = new ArrayList<String>();
        col2StringList.add("Row2");
        col2StringList.add("Row1");
        list.add(new Column("A", col1IntList));
        list.add(new Column("B", col2StringList));
        test = new DataFrame(list);
        
        assertFalse(defaultDataFrame.equals(test));
    }
    
    @Test
    public void testDisplay() {
        
        
        /*
        * We can't get to the code that return "Trying to print an empty dataset"
        * but we let it anyway for protection.
        */
        /*ArrayList<Column> list = new ArrayList<Column>();
        ArrayList<Integer> col1IntList = new ArrayList<>();
        col1IntList.add(1);
        list.add(new Column("A", col1IntList));
        
        DataFrame data = new DataFrame(list);
        ArrayList<String> columnNames = new ArrayList<String>();
        columnNames.add("B");
        data = data.getColumnSubset(columnNames);
        
        String expected = "Trying to print an empty dataset";
        assertEquals(expected.replaceAll("\\s+", ""), data.display().replaceAll("\\s+", ""));*/
    }
    
    @Test
    public void testCreationFromColumns() {
        ArrayList<Column> list = new ArrayList<>();
        ArrayList<Integer> col1IntList = new ArrayList<>();
        col1IntList.add(1);
        col1IntList.add(2);
        ArrayList<String> col2StringList = new ArrayList<>();
        col2StringList.add("Row1");
        col2StringList.add("Row2");
        list.add(new Column("A", col1IntList));
        list.add(new Column("B", col2StringList));
        DataFrame data = new DataFrame(list);
        String expected = "A B 1 Row1 2 Row2";
        assertEquals(expected.replaceAll("\\s+", ""), data.display().replaceAll("\\s+", ""));
    }

    @Test
    public void testCreationFromValues() {
        //Créer un dataset à partir de noms et de données.
        ArrayList<String> names = new ArrayList<>();
        names.add("A");
        names.add("B");
        names.add("C");

        ArrayList<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);

        ArrayList<Integer> col2 = new ArrayList<>();
        col2.add(2);
        col2.add(2);
        col2.add(2);
        ArrayList<String> col3 = new ArrayList<>();
        col3.add("Row1");
        col3.add("Row2");
        col3.add("Row3");

        List<List<? extends Comparable>> l2 = new ArrayList<>();
        l2.add(col1);
        l2.add(col2);
        l2.add(col3);

        DataFrame data = new DataFrame(names, l2);
        String expected = "A B C 1 2 Row1 2 2 Row2 3 2 Row3";
        assertEquals(expected.replaceAll("\\s+", ""), data.display().replaceAll("\\s+", ""));
    }

    @Test(expected = ColumnsNotSameSizeException.class)
    public void testExceptionColumnsNotSameSize() {
        //Créer un dataset à partir de noms et de données.
        ArrayList<String> names = new ArrayList<>();
        names.add("A");
        names.add("B");
        names.add("C");

        ArrayList<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);

        ArrayList<Integer> col2 = new ArrayList<>();
        col2.add(2);
        col2.add(2);
        col2.add(2);
        ArrayList<String> col3 = new ArrayList<>();
        col3.add("Row1");
        col3.add("Row2");

        List<List<? extends Comparable>> l2 = new ArrayList<>();
        l2.add(col1);
        l2.add(col2);
        l2.add(col3);

        DataFrame data = new DataFrame(names, l2);

    }

    @Test(expected = NotAsMuchNamesAsColumnsException.class)
    public void testNotAsMuchNamesAsColumns() {
        //Créer un dataset à partir de noms et de données.
        ArrayList<String> names = new ArrayList<>();
        names.add("A");
        names.add("B");
        names.add("C");

        ArrayList<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);

        ArrayList<Integer> col2 = new ArrayList<>();
        col2.add(2);
        col2.add(2);
        col2.add(2);
        ArrayList<String> col3 = new ArrayList<>();
        col3.add("Row1");
        col3.add("Row2");
        col3.add("Row3");

        List<List<? extends Comparable>> l2 = new ArrayList<>();
        l2.add(col1);
        l2.add(col2);

        DataFrame data = new DataFrame(names, l2);

    }

    @Test(expected = NameAlreadyDefinedException.class)
    public void testNameAlreadyDefined() {
        //Créer un dataset à partir de noms et de données.
        ArrayList<String> names = new ArrayList<>();
        names.add("A");
        names.add("A");
        names.add("B");

        ArrayList<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);

        ArrayList<Integer> col2 = new ArrayList<>();
        col2.add(2);
        col2.add(2);
        col2.add(2);
        ArrayList<String> col3 = new ArrayList<>();
        col3.add("Row1");
        col3.add("Row2");
        col3.add("Row3");

        List<List<? extends Comparable>> l2 = new ArrayList<>();
        l2.add(col1);
        l2.add(col2);
        l2.add(col3);

        DataFrame data = new DataFrame(names, l2);

    }

    @Test
    public void testHeadNoArgs() {
        List<Column> actualCol = new ArrayList<>();
        List<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);
        col1.add(4);
        col1.add(5);
        col1.add(6);
        col1.add(7);
        col1.add(8);
        col1.add(9);
        actualCol.add(new Column("A", col1));
        DataFrame data = new DataFrame(actualCol);

        String expected = "A 1 2 3 4 5";

        assertEquals(expected.replaceAll("\\s+", ""), data.head().replaceAll("\\s+", ""));

    }

    @Test
    public void testHead2() {
        List<Column> actualCol = new ArrayList<>();
        List<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);
        col1.add(4);
        col1.add(5);
        col1.add(6);
        col1.add(7);
        col1.add(8);
        col1.add(9);
        actualCol.add(new Column("A", col1));
        DataFrame data = new DataFrame(actualCol);

        String expected = "A 1 2";

        assertEquals(expected.replaceAll("\\s+", ""), data.head(2).replaceAll("\\s+", ""));

    }

    @Test
    public void testHeadMoreThanNumberOfValues() {
        List<Column> actualCol = new ArrayList<>();
        List<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);
        col1.add(4);
        col1.add(5);
        col1.add(6);
        col1.add(7);
        col1.add(8);
        col1.add(9);
        actualCol.add(new Column("A", col1));
        DataFrame data = new DataFrame(actualCol);

        assertEquals(data.display().replaceAll("\\s+", ""), data.head(100).replaceAll("\\s+", ""));

    }

    @Test
    public void testTailNoArgs() {
        List<Column> actualCol = new ArrayList<>();
        List<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);
        col1.add(4);
        col1.add(5);
        col1.add(6);
        col1.add(7);
        col1.add(8);
        col1.add(9);
        actualCol.add(new Column("A", col1));
        DataFrame data = new DataFrame(actualCol);

        String expected = "A 5 6 7 8 9";

        assertEquals(expected.replaceAll("\\s+", ""), data.tail().replaceAll("\\s+", ""));

    }

    @Test
    public void testTail2() {
        List<Column> actualCol = new ArrayList<>();
        List<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);
        col1.add(4);
        col1.add(5);
        col1.add(6);
        col1.add(7);
        col1.add(8);
        col1.add(9);
        actualCol.add(new Column("A", col1));
        DataFrame data = new DataFrame(actualCol);

        String expected = "A 8 9";

        assertEquals(expected.replaceAll("\\s+", ""), data.tail(2).replaceAll("\\s+", ""));

    }

    @Test
    public void testTailMoreThanNumberOfValues() {
        List<Column> actualCol = new ArrayList<>();
        List<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);
        col1.add(4);
        col1.add(5);
        col1.add(6);
        col1.add(7);
        col1.add(8);
        col1.add(9);
        actualCol.add(new Column("A", col1));
        DataFrame data = new DataFrame(actualCol);

        assertEquals(data.display().replaceAll("\\s+", ""), data.tail(100).replaceAll("\\s+", ""));

    }

    @Test
    public void testGetLineSubset() {
        List<Column> actualCol = new ArrayList<>();
        List<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);
        col1.add(4);
        col1.add(5);
        col1.add(6);
        col1.add(7);
        col1.add(8);
        col1.add(9);
        actualCol.add(new Column("A", col1));
        DataFrame data = new DataFrame(actualCol);

        List<Column> expectedCol = new ArrayList<>();
        List<Integer> colE1 = new ArrayList<>();
        colE1.add(6);
        colE1.add(7);
        colE1.add(8);
        colE1.add(9);
        expectedCol.add(new Column("A", colE1));
        DataFrame expectedData = new DataFrame(expectedCol);

        assertEquals(expectedData, data.getLineSubset(5));

    }
    
    @Test
    public void testGetLineSubsetBiggerThanPossible() {
        List<Column> actualCol = new ArrayList<>();
        List<Integer> col1 = new ArrayList<>();
        col1.add(1);
        col1.add(2);
        col1.add(3);
        col1.add(4);
        col1.add(5);
        col1.add(6);
        col1.add(7);
        col1.add(8);
        col1.add(9);
        actualCol.add(new Column("A", col1));
        DataFrame data = new DataFrame(actualCol);

        assertEquals(data, data.getLineSubset(100));

    }

    @Test (expected = IOException.class)
    public void testDataFrameFromInvalidFile() throws IOException {
        
        DataFrame data = new DataFrame("");
    }
    
    @Test (expected = NoColumnsException.class)
    public void testDataFrameFromEmptyFile() throws IOException {
        
        String fileName = "testDataFrameFile.dat";
        
        final File testFile = new File(fileName);
        if(testFile.exists()) {
            testFile.delete();
        }
        
        testFile.createNewFile();
        
        DataFrame data = new DataFrame(testFile.getName());
    }
    
    @Test
    public void testDataFrameFromCsvFile() throws IOException {
        
        String fileName = "testDataFrameFile.csv";
        
        final File testFile = new File(fileName);
        if(testFile.exists()) {
            testFile.delete();
        }
        
        testFile.createNewFile();
        
        ArrayList<Column> list = new ArrayList<Column>();
        
        ArrayList<Integer> col1IntList = new ArrayList<Integer>();
        col1IntList.add(2);
        col1IntList.add(1);
        
        ArrayList<String> col2StringList = new ArrayList<String>();
        col2StringList.add("Row1");
        col2StringList.add("Row2");
        
        ArrayList<Date> col3DateList = new ArrayList<Date>();
        col3DateList.add(new Date());
        col3DateList.add(new Date(0));
        
        ArrayList<Double> col4DoubleList = new ArrayList<Double>();
        col4DoubleList.add(52.06);
        col4DoubleList.add(0.05);
        
        list.add(new Column("Ints", col1IntList));
        list.add(new Column("Strings", col2StringList));
        list.add(new Column("Dates", col3DateList));
        list.add(new Column("Doubles", col4DoubleList));
        
        try (
                PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                ) {
            
            for(Column col : list) {
                writer.print(col.getName() + ",");
            }
            writer.println();
            
            int j;
            for(int i = 0; i < list.get(0).getData().size(); ++i) {
                
                for(j = 0; j < list.size(); ++j) {
                    
                    Column col = list.get(j);
                    writer.print(col.getData().get(i).toString() + ",");
                }
                writer.println();
            }
            
            writer.flush();
            
            DataFrame expectedData = new DataFrame(list);
            DataFrame data = new DataFrame(fileName);
            
            assertEquals(expectedData, data);
        }
    }
}
