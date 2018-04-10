package fr.brokennightmareteam.jpandas;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.brokennightmareteam.jpandas.dataframe.DataFrame;

public class TestSubDataFrame {

	DataFrame dfSource;
	DataFrame dfTarget;
	
	@Before
	public void init(){
		List<String> columnsName = Arrays.asList("Name","Age","Poid","EstBlond");
		Comparable<?>[] names = {"Isabelle","Gaspard","Léo","Arthur"};
		Comparable<?>[] ages = {25,54,17,33};
		Comparable<?>[] poids = {73.2,64.5,55.8,71.9};
		Comparable<?>[] estBlond = {true,false,true,false};
		dfSource = new DataFrame(columnsName, names, ages, poids, estBlond);
		dfTarget = new DataFrame();
	}

	@Test
	public void testSubDataFrameColumns() {
		dfTarget = dfSource.subDataFrameFromColumn(new String[]{"Name","Age"});
		assertEquals("     || Name     || Age ||\n" + 
				 "|| 0 || Isabelle || 25  ||\n" + 
				 "|| 1 || Gaspard  || 54  ||\n" + 
				 "|| 2 || Léo      || 17  ||\n" + 
				 "|| 3 || Arthur   || 33  ||\n" + 
				 "\n",dfTarget.print());
	}
	
	@Test
	public void testSubDataFrameLines() {
		dfTarget = dfSource.subDataFrameFromLine(1, 3);
		assertEquals("     || Name    || Age || Poid || EstBlond ||\n" + 
				 	 "|| 0 || Gaspard || 54  || 64.5 || false    ||\n" + 
				 	 "|| 1 || Léo     || 17  || 55.8 || true     ||\n" + 
				 	 "\n",dfTarget.print());
	}
}
