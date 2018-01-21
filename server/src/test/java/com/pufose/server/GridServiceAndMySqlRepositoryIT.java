package com.pufose.server;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("mysql")
public class GridServiceAndMySqlRepositoryIT {


	
	@Autowired
	private GridService service;

	@Before
	public void setUp() {
		List<String> ids=service.getAllId();
		ids.forEach(id -> service.dropTable(Integer.parseInt(id)));
	}
	@After
	public void tearDown() {
		List<String> ids=service.getAllId();
		ids.forEach(id -> service.dropTable(Integer.parseInt(id)));
	}
	@Test
	public void testGetAllIdWhenDbEmpty() {
		assertEquals(0,service.getAllId().size());
	}
	
	@Test
	public void testGetAllIdWhenDbContainsOne() {
		service.storeInDb(new DatabaseGrid(0));
		List<String> ids=service.getAllId();
		assertEquals(1,ids.size());
		assertEquals("0",ids.get(0));
	}
	@Test
	public void testGetAllIdWhenDbContainsMore() {
		service.storeInDb(new DatabaseGrid(0));
		service.storeInDb(new DatabaseGrid(1));
		List<String> ids=service.getAllId();
		assertEquals(2,ids.size());
		assertEquals("0",ids.get(0));
		assertEquals("1",ids.get(1));
	}
	@Test
	public void testGetByIdWhenExists() {
		DatabaseGrid expected;
		service.storeInDb(expected=new DatabaseGrid(0));
		assertEquals(expected,service.getById(0));
	}
	@Test
	public void testGetByIdWhenNotExists() {
		assertNull(service.getById(0));
	}
	@Test
	public void testGetShortestPathWhenIsEmpty() {
		service.storeInDb(new DatabaseGrid(10));
		List<String> path=service.getShortestPath("0", "1", 10);
		assertEquals(0,path.size());
		
	}
	@Test
	public void testGetShortestPathTwice() {
		service.storeInDb(new DatabaseGrid(new int[][] {{1,1},{1,1}},7));
		List<String> first=service.getShortestPath("0_0", "0_1", 7);
		List<String> second=service.getShortestPath("0_0", "0_1", 7);
		assertEquals(Arrays.asList("0_0","0_1"),first);
		assertEquals(Arrays.asList("0_0","0_1"),second);
	}
	@Test
	public void testGetShortestPathWhenIsLengthOne() {
		service.storeInDb(new DatabaseGrid(10));
		List<String> path=service.getShortestPath("0", "0", 10);
		assertEquals(1,path.size());
		assertEquals("0",path.get(0));
		
	}
	@Test
	public void testGetShortestPathWhenIsLengthMoreThanOne() {
		int[][]mat;
		mat=new int[][] {
			{1,1,1},
			{1,1,1},
			{1,1,1}
		};
		service.storeInDb(new DatabaseGrid(mat,1));
		List<String> path=service.getShortestPath("0_0", "2_1", 1);
		assertEquals(Arrays.asList("0_0","1_0","2_0","2_1"),path);
	}
	@Test
	public void testNextIdWhenDbIsEmpty() {
		assertEquals(1,service.nextId());
	}
	private void addElements(GridService serv, int i) {
		for(int j=0; j<i;j++)
		{
			serv.storeInDb(new DatabaseGrid(j));
		}
	}
	
	@Test
	public void testNextIdWhenDbHasOneGrid() {
		addElements(service,1);
		assertEquals(1,service.nextId());
	}
	@Test
	public void testNextIdWhenDbHasMoreGrids() {
		addElements(service,5);
		assertEquals(5,service.nextId());
		service.storeInDb(new DatabaseGrid(20));
		assertEquals(21,service.nextId());
	}

}