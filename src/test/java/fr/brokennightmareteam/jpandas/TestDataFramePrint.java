package fr.brokennightmareteam.jpandas;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.brokennightmareteam.jpandas.dataframe.DataFrame;

public class TestDataFramePrint {

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
	public void testPrintAll() {
		assertEquals("     || Name     || Age || Poid || EstBlond ||\n" + 
					 "|| 0 || Isabelle || 25  || 73.2 || true     ||\n" + 
					 "|| 1 || Gaspard  || 54  || 64.5 || false    ||\n" + 
					 "|| 2 || Léo      || 17  || 55.8 || false    ||\n" + 
					 "|| 3 || Arthur   || 33  || 71.9 || true     ||\n" + 
					 "\n",df.print());
	}
	
	@Test
	public void testPrintFirstLines() {
		assertEquals("     || Name     || Age || Poid || EstBlond ||\n" + 
					 "|| 0 || Isabelle || 25  || 73.2 || true     ||\n" + 
					 "|| 1 || Gaspard  || 54  || 64.5 || false    ||\n" + 
					 "\n",df.printFirstLine(2));
	}
	
	@Test
	public void testPrintLastLines() {
		assertEquals("     || Name   || Age || Poid || EstBlond ||\n" +  
					 "|| 2 || Léo    || 17  || 55.8 || false    ||\n" + 
					 "|| 3 || Arthur || 33  || 71.9 || true     ||\n" + 
					 "\n",df.printLastLine(2));
	}
	
	@Test
	public void testPrintColumns() {
		assertEquals("     || Name     || Poid ||\n" + 
					 "|| 0 || Isabelle || 73.2 ||\n" + 
					 "|| 1 || Gaspard  || 64.5 ||\n" + 
					 "|| 2 || Léo      || 55.8 ||\n" + 
					 "|| 3 || Arthur   || 71.9 ||\n" + 
					 "\n",df.printColumns(Arrays.asList("Name","Poid")));
	}
	
	@Test
	public void testPrintFirstLinesOnColumnsList() {
		assertEquals("     || Age || Poid ||\n" + 
					 "|| 0 || 25  || 73.2 ||\n" + 
					 "|| 1 || 54  || 64.5 ||\n" + 
					 "\n",df.printFirstLineOnColumnList(2, Arrays.asList("Age","Poid")));
	}
	
	@Test
	public void testPrintLastLinesOnColumnsList() {
		assertEquals("     || EstBlond ||\n" +  
					 "|| 2 || false    ||\n" + 
					 "|| 3 || true     ||\n" + 
					 "\n",df.printLastLineOnColumnList(2, Arrays.asList("EstBlond")));
	}
	
	@Test
	public void testSubPrint() {
		assertEquals("     || Age || EstBlond ||\n" +  
					 "|| 1 || 54  || false    ||\n" + 
					 "|| 2 || 17  || false    ||\n" + 
					 "\n",df.subprint(1, 3, Arrays.asList("Age","EstBlond")));
	}
}
