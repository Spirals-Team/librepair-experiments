package fr.brokennightmareteam.jpandas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import fr.brokennightmareteam.jpandas.dataframe.DataFrame;
import junit.framework.TestCase;

public class TestDateFrameCreate extends TestCase{
	
    
	@Test
	public void testEmptyDataFrame() {
		DataFrame df = new DataFrame();
		assertTrue(df.isEmpty());
	}
	
	public void testConstWithDifferentNumberOfColumnsFromList() {
		List<String> columnsName = Arrays.asList("1","2","3");
		List<List<Comparable<?>>> data = new ArrayList<List<Comparable<?>>>();
		data.add(Arrays.asList("item1","item2"));
		data.add(Arrays.asList(1,2,3));
		try{
			new DataFrame(columnsName, data);
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertTrue(e.getMessage().equals("Nombre de colonnes incorrect"));
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstWithDifferentNumberOfColumnsFromFile() throws IOException {
		try{
			new DataFrame(new File(this.getClass().getResource("/DifferentNumberOfColumns.csv").getFile()));
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e){
			assertTrue(e.getMessage().equals("Format de fichier incorrect"));
		} catch (Exception e){
			fail("Should raise IllegalArgumentException");
		}
	}

}
