package com.pufose.server;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphTest {

	private Graph g;
	private String node1;
	private String node2;
	private String node3;
	private String node4;
	private String node5;
	private String node6;
	private String node7;
	private List<String> minPath;
	private List<String> emptyMinPath;

	@Before
	public void setUp() {
		g = new Graph();
		node1 = "nodo1";
		node2 = "nodo2";
		node3 = "nodo3";
		node4 = "nodo4";
		node5 = "nodo5";
		node6 = "nodo6";
		node7 = "nodo7";
		minPath = new LinkedList<>();
		emptyMinPath = new LinkedList<>();
	}

	@After
	public void tearDown() {
		g.removeAllNodes();
	}
	@Test(expected = IllegalArgumentException.class)
	public void addEdgeWhenFirstNodeIsNotInListTest() {
		addNodesToList();
		g.addEdge(node3, node1);

	}

	@Test(expected = IllegalArgumentException.class)
	public void addEdgeWhenSecondNodeIsNotInListTest() {
		addNodesToList();
		g.addEdge(node1, node3);
		
	}
	@Test
	public void removeNodesTest() {
		addNodesToList();
		g.removeAllNodes();
		assertEquals(0,g.getNodes().size());
	}
	@Test
	public void addEdgeWhenNodeAreInListTest() {
		addNodesToList();
		g.addEdge(node1, node2);
		String[] actualEdge=g.getEdges().get(0);
		assertArrayEquals(actualEdge, new String[]{node1, node2});
	}

	@Test
	public void minPathFromEqualByToTest() {
		addNodesToList();
		g.addEdge(node1,node2);
		String expected=node1;
		assertEquals(g.minPath(node1, node1), Arrays.asList(expected));
	}

	@Test
	public void minPathFromDistinctByToTest() {
		insertMoreIteminList();
		addEdgesForMinPath();
		addNodesToExpectedMinPath();
		assertEquals(g.minPath(node6, node1), minPath);
	}
	
	@Test
	public void minPathDoesNotExistsTest() {
		insertMoreIteminList();
		addEdgesForMinPath();
		addNodesToExpectedMinPath();
		assertEquals(g.minPath(node6, node7), emptyMinPath);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addNodeTwiceTest() {
		g.addNodes(node1);
		g.addNodes(node1);
	}
	
	private void addNodesToExpectedMinPath() {
		minPath.add(node6);
		minPath.add(node4);
		minPath.add(node5);
		minPath.add(node1);
	}

	private void addEdgesForMinPath() {
		g.addEdge(node6,node4);
		g.addEdge(node4,node5);
		g.addEdge(node4,node3);
		g.addEdge(node3,node2);
		g.addEdge(node5,node2);
		g.addEdge(node5,node1);
		g.addEdge(node2,node1);
	}

	private void insertMoreIteminList() {
		g.addNodes(node1);
		g.addNodes(node2);
		g.addNodes(node3);
		g.addNodes(node4);
		g.addNodes(node5);
		g.addNodes(node6);
		g.addNodes(node7);
	}

	private void addNodesToList() {
		g.addNodes(node1);
		g.addNodes(node2);
	}

	

}
