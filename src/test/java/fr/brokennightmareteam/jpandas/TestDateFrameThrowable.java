package fr.brokennightmareteam.jpandas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import fr.brokennightmareteam.jpandas.dataframe.DataFrame;
import junit.framework.TestCase;

public class TestDateFrameThrowable extends TestCase{
	
    public TestDateFrameThrowable(String testName){
        super(testName);
    }
    
	@Test
	public void testCreateWithDifferentNumberOfColumnsFromArrays1() {
		List<String> columnsName = Arrays.asList("1","2","3");
		Comparable<?>[] column1 = {"item1","item2","item3"};
		Comparable<?>[] column2 = {1,2,3};
		try{
			new DataFrame(columnsName, column1, column2);
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Nombre de colonnes incorrect");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}
	
	@Test
	public void testCreateWithDifferentNumberOfColumnsFromArrays2() {
		List<String> columnsName = Arrays.asList("1","2","3");
		Comparable<?>[] column1 = {"item1","item2","item3"};
		Comparable<?>[] column2 = {1,2,3};
		Comparable<?>[] column3 = {1.0,2.0,3.0};
		Comparable<?>[] column4 = {true,true,false};
		try{
			new DataFrame(columnsName, column1, column2, column3, column4);
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Nombre de colonnes incorrect");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}
	
	@Test
	public void testCreateWithDifferentNumberOfColumnsFromList1() {
		List<String> columnsName = Arrays.asList("1","2","3");
		List<List<Comparable<?>>> data = new ArrayList<List<Comparable<?>>>();
		data.add(Arrays.asList("item1","item2","item3"));
		data.add(Arrays.asList(1,2,3));
		try{
			new DataFrame(columnsName, data);
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Nombre de colonnes incorrect");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}
	
	
	@Test
	public void testCreateWithDifferentNumberOfColumnsFromList2() {
		List<String> columnsName = Arrays.asList("1","2","3");
		List<List<Comparable<?>>> data = new ArrayList<List<Comparable<?>>>();
		data.add(Arrays.asList("item1","item2","item3"));
		data.add(Arrays.asList(1,2,3));
		data.add(Arrays.asList(1.0,1.2,2.2));
		data.add(Arrays.asList(true,false,true));
		try{
			new DataFrame(columnsName, data);
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Nombre de colonnes incorrect");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}

	@Test
	public void testCreateWithDifferentNumberOfColumnsFromFileName1() throws IOException {
		try{
			new DataFrame(this.getClass().getResource("/DifferentNumberOfColumns1.csv").getFile());
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Format de fichier incorrect");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}
	
	@Test
	public void testCreateWithDifferentNumberOfColumnsFromFileName2() throws IOException {
		try{
			new DataFrame(this.getClass().getResource("/DifferentNumberOfColumns2.csv").getFile());
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Format de fichier incorrect");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}
	
	@Test
	public void testCreateWithFileBadFormatFirstLine() throws IOException {
		try{
			new DataFrame(this.getClass().getResource("/BadFormatFirstLine.csv").getFile());
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Format de fichier incorrect");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}
	
	@Test
	public void testCreateWithFileBadFormatLastLine() throws IOException {
		try{
			new DataFrame(this.getClass().getResource("/BadFormatLastLine.csv").getFile());
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Format de fichier incorrect");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}
	
	@Test
	public void testCreateWithBadTypeInteger() throws IOException {
		try{
			new DataFrame(this.getClass().getResource("/BadTypeInteger.csv").getFile());
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Types incorrect expected Integer");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}
	
	@Test
	public void testCreateWithBadTypeDouble() throws IOException {
		try{
			new DataFrame(this.getClass().getResource("/BadTypeDouble.csv").getFile());
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Types incorrect expected Double");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}
	
	@Test
	public void testCreateWithBadTypeBoolean() throws IOException {
		try{
			new DataFrame(this.getClass().getResource("/BadTypeBoolean.csv").getFile());
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertEquals(e.getMessage(),"Types incorrect expected Boolean");
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}

}
