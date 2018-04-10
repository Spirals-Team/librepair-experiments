package fr.brokennightmareteam.jpandas;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.brokennightmareteam.jpandas.dataframe.DataFrame;

public class TestDataFrameFonctionnalite {

	DataFrame df;
	
	@Before
	public void init(){
		List<String> columnsName = Arrays.asList("Name","Age","Poid","EstBlond");
		Comparable<?>[] names = {"Isabelle","Gaspard","Léo","Arthur"};
		Comparable<?>[] ages = {25,54,17,33};
		Comparable<?>[] poids = {73.2,64.5,55.8,71.9};
		Comparable<?>[] estBlond = {true,false,false,true};
		df = new DataFrame(columnsName, names, ages, poids, estBlond);
	}
	
	@Test
	public void testMinString() {
		assertEquals(df.minCol("Name"),"Arthur");
	}
	
	@Test
	public void testMinInteger() {
		assertEquals(17,df.minCol("Age"));
	}
	
	@Test
	public void testMinDouble() {
		assertEquals(55.8,df.minCol("Poid"));
	}
	
	@Test
	public void testMinBoolean() {
		assertEquals(false,df.minCol("EstBlond"));
	}
	
	@Test
	public void testMaxString() {
		assertEquals("Léo",df.maxCol("Name"));
	}
	
	@Test
	public void testMaxInteger() {
		assertEquals(54,df.maxCol("Age"));
	}
	
	@Test
	public void testMaxDouble() {
		assertEquals(73.2,df.maxCol("Poid"));
	}
	
	@Test
	public void testMaxBoolean() {
		assertEquals(true,df.maxCol("EstBlond"));
	}
	
	@Test
	public void testAvgString() {
		assertEquals(6.0,df.avgCol("Name"),0.00001);
	}
	
	@Test
	public void testAvgInteger() {
		assertEquals(32.25,df.avgCol("Age"),0.00001);
	}
	
	@Test
	public void testAvgDouble() {
		assertEquals(66.35,df.avgCol("Poid"),0.00001);
	}

	@Test
	public void testAvgBoolean() {
		assertEquals(0.5,df.avgCol("EstBlond"),0.00001);
	}
}
