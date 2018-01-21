package com.pufose.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("mongo")
public class GridServiceAndMongoRepositoryIT {

	@Autowired
	private IMongoRepository repo;
	
	@Autowired
	private GridService service;

	
	@Test
	public void testGetAllIdWhenDbEmpty() {
		assertEquals(0,service.getAllId().size());
	}
	
	@After
	public void tearDown() {
		repo.deleteAll();
	}
	@Test
	public void testGetAllIdWhenDbContainsOne() {
		repo.save(new DatabaseGrid(0));
		List<String> ids=service.getAllId();
		assertEquals(1,ids.size());
		assertEquals("0",ids.get(0));
	}
	@Test
	public void testGetAllIdWhenDbContainsMore() {
		repo.save(new DatabaseGrid(0));
		repo.save(new DatabaseGrid(1));
		List<String> ids=service.getAllId();
		assertEquals(2,ids.size());
		assertEquals("0",ids.get(0));
		assertEquals("1",ids.get(1));
	}
	@Test
	public void testGetByIdWhenExists() {
		DatabaseGrid expected;
		expected=repo.save(new DatabaseGrid(0));
		assertEquals(expected,service.getById(0));
	}
	@Test
	public void testGetByIdWhenNotExists() {
		assertNull(service.getById(0));
	}
	@Test
	public void testGetShortestPathWhenIsEmpty() {
		repo.save(new DatabaseGrid(10));
		List<String> path=service.getShortestPath("0", "1", 10);
		assertEquals(0,path.size());
		
	}
	@Test
	public void testGetShortestPathTwice() {
		repo.save(new DatabaseGrid(new int[][] {{1,1},{1,1}},0));
		List<String> first=service.getShortestPath("0_0", "0_1", 0);
		List<String> second=service.getShortestPath("0_0", "0_1", 0);
		assertEquals(Arrays.asList("0_0","0_1"),first);
		assertEquals(Arrays.asList("0_0","0_1"),second);
	}
	@Test
	public void testGetShortestPathWhenIsLengthOne() {
		repo.save(new DatabaseGrid(10));
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
		repo.save(new DatabaseGrid(mat,1));
		List<String> path=service.getShortestPath("0_0", "2_1", 1);
		assertEquals(Arrays.asList("0_0","1_0","2_0","2_1"),path);
	}
	@Test
	public void testNextIdWhenDbIsEmpty() {
		assertEquals(1,service.nextId());
	}
	private void addElements(IMongoRepository repo, int i) {
		for(int j=0; j<i;j++)
		{
			repo.save(new DatabaseGrid(j));
		}
	}
	
	@Test
	public void testNextIdWhenDbHasOneGrid() {
		repo.deleteAll();
		addElements(repo,1);
		assertEquals(1,service.nextId());
	}
	@Test
	public void testNextIdWhenDbHasMoreGrids() {
		addElements(repo,5);
		assertEquals(5,service.nextId());
		repo.save(new DatabaseGrid(20));
		assertEquals(21,service.nextId());
	}

}
