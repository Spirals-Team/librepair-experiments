package src;

import devopsproject.DataFrame;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

public class TestDataframe {

    DataFrame dfArray;
    List<DataFrame> dfFileList;
    DataFrame dfBase;

    public TestDataframe() throws IOException{
        /* Test base constructor dataframe creation */
        dfBase = new DataFrame();

        /* Test array constructor dataframe creation */
        String[] labels = new String[3];
        labels[0] = String.valueOf('a');
        labels[1] = String.valueOf('b');
        labels[2] = String.valueOf('c');

        List<String> element1 = new ArrayList<>();
        element1.add("s1");
        element1.add("s2");
        element1.add("s3");

        List<Integer> element2 = new ArrayList<>();
        element2.add(1);
        element2.add(2);
        element2.add(3);

        List<Float> element3 = new ArrayList<>();
        element3.add((float) 1.4);
        element3.add((float) 2.4);
        element3.add((float) 3.5);

        List<List> elements = new ArrayList<>();
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);

        dfArray = new DataFrame(labels, elements);

        /* Test file constructor dataframe creation */
        dfFileList = new ArrayList<>();
        dfFileList.add(new DataFrame("tests/resources/test.csv", ","));
        dfFileList.add(new DataFrame("tests/resources/test1.csv", ","));
        dfFileList.add(new DataFrame("tests/resources/test2.csv", ","));
        dfFileList.add(new DataFrame("tests/resources/file1.csv", ","));
        dfFileList.add(new DataFrame("tests/resources/estate_data.csv", ","));
        dfFileList.add(new DataFrame("tests/resources/sales.csv", ","));
    }

    @Test
    public void showTest() {
        /* Test show of base dataframe */
        dfBase.show();

        /* Test show of dataframe created from array */
        dfArray.show();

        /* Test show of dataframe created from file */
        for (DataFrame dfObject : dfFileList) {
            dfObject.show();
        }
    }

    @Test
    public void headTestGoodValues() {
        dfBase.head(0);
        dfBase.head(dfBase.getMaxColumnSize() / 2);

        dfArray.head(0);
        dfArray.head(dfBase.getMaxColumnSize() / 2);

        for (DataFrame dfObject : dfFileList) {
            dfObject.head(0);
            dfObject.head(dfBase.getMaxColumnSize() / 2);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void headTestValuesTooBig(){
        dfBase.head(Integer.MAX_VALUE);
        
        dfArray.head(Integer.MAX_VALUE);
        
        for (DataFrame dfObject : dfFileList){
            dfObject.head(Integer.MAX_VALUE);
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void headTestNegativeValues(){
        dfBase.head(-1);
        
        dfArray.head(-1);
        
        for (DataFrame dfObject : dfFileList){
            dfObject.head(-1);
        }
    }
    
    @Test
    public void tailTestGoodValues() {
        dfBase.tail(0);
        dfBase.tail(dfBase.getMaxColumnSize() / 2);

        dfArray.tail(0);
        dfArray.tail(dfBase.getMaxColumnSize() / 2);

        for (DataFrame dfObject : dfFileList) {
            dfObject.tail(0);
            dfObject.tail(dfBase.getMaxColumnSize() / 2);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void tailTestValuesTooBig(){
        dfBase.tail(Integer.MAX_VALUE);
        
        dfBase.tail(Integer.MAX_VALUE);
        
        for (DataFrame dfObject : dfFileList){
            dfObject.tail(Integer.MAX_VALUE);
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void tailTestNegativeValues(){
        dfBase.tail(-1);
        
        dfArray.tail(-1);
        
        for (DataFrame dfObject : dfFileList){
            dfObject.tail(-1);
        }
    }
    
    @Test(expected = IOException.class)
    public void wrongFileExtension() throws IOException{
        dfFileList.add(new DataFrame("tests/resources/Prueba.txt", ","));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void typeError() throws IOException{
        dfFileList.add(new DataFrame("tests/resources/type_error.csv", ","));
    }
    
    @Test
    public void headLabelExistsTest(){
        dfFileList.get(0).head("Nom", 2);
        dfFileList.get(1).head("Nom", 3);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void headLabelNotAvailable(){
        dfFileList.get(0).head("Hello",2);
        dfFileList.get(1).head("Hello",3);
    }
    
    @Test
    public void tailLabelExistsTest(){
        dfFileList.get(0).tail("Nom", 2);
        dfFileList.get(1).tail("Nom", 3);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void tailLabelNotAvailable(){
        dfFileList.get(0).tail("Hello",2);
        dfFileList.get(1).tail("Hello",3);
    }
    
    @Test
    public void locTestCorrect(){
        dfFileList.get(0).loc("Nom");
        dfFileList.get(0).loc("Nom","Age");
        dfFileList.get(5).loc("Product","Name","City");
        List<String> labels = new ArrayList<>();
        labels.add("Name");
        labels.add("Price");
        dfFileList.get(5).loc(labels);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void locLabelTestIncorrect(){
        dfFileList.get(0).loc("Hello");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void locMultipleLabelsTestIncorrect(){
        dfFileList.get(0).loc("Hello","Nom","City");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void locLabelListTestIncorrect(){
        List<String> labels = new ArrayList<>();
        labels.add("Hello");
        labels.add("Price");
        dfFileList.get(5).loc(labels);
    }
    
    @Test
    public void locLabelInfSupSwitched(){
        dfFileList.get(5).loc("City","Product");
    }
    
    @Test
    public void ilocTestCorrect(){
        dfFileList.get(5).iloc(3);
        dfFileList.get(5).iloc(1,3);
        dfFileList.get(5).iloc(2,2);
        dfFileList.get(5).iloc(2,4,5);
        List<Integer> indexes = new ArrayList<>();
        indexes.add(4);
        dfFileList.get(5).iloc(indexes);
    }
    
    @Test
    public void ilocInfSupDifferentOrder(){
        dfFileList.get(5).iloc(5, 2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexNegative(){
        dfFileList.get(5).iloc(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexTooBig(){
        dfFileList.get(5).iloc(Integer.MAX_VALUE);
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexesInfSupValueTooHigh(){
        dfFileList.get(5).iloc(2, Integer.MAX_VALUE);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexesInfSupNegative(){
        dfFileList.get(5).iloc(-1,2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocMultipleIndexesNegative(){
        dfFileList.get(5).iloc(-1,5,3);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocMultipleIndexesTooBig(){
        dfFileList.get(5).iloc(2,5,Integer.MAX_VALUE);
    }
    
    @Test
    public void ilocMultipleIndexes(){
        dfFileList.get(5).iloc(5, 2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocListIndexesTooBig(){
        List<Integer> indexes = new ArrayList<>();
        indexes.add(Integer.MAX_VALUE);
        indexes.add(1);
        dfFileList.get(5).iloc(indexes);
    }
    
        @Test(expected = IllegalArgumentException.class)
    public void ilocListIndexesNegative(){
        List<Integer> indexes = new ArrayList<>();
        indexes.add(-1);
        indexes.add(1);
        dfFileList.get(5).iloc(indexes);
    }
    
    @Test
    public void showStatisticsCorrect() {
        dfArray.showStatitic("c");
        
        dfFileList.get(0).showStatitic("Age");
        dfFileList.get(5).showStatitic("Price");
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void showStatisticNullTable(){
        dfBase.showStatitic("Nom");
    }

    @Test(expected = IllegalArgumentException.class)
    public void showStatisticNotNumeric(){
        dfFileList.get(0).showStatitic("Nom");
    }

}

