package com.pufose.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GridServiceTest {


	@Mock
	private IServiceImplementor impl;


	@InjectMocks
	private GridService gridService;

	@Mock
	private Graph tobuild;


	@Test
	public void getMinPathTest() {
		DatabaseGrid spied;
		int[][] mat = new int[][] { { 1, 1 }, { 0, 1 }, };
		given(impl.getById(1)).willReturn(spied = Mockito.spy(new DatabaseGrid(mat, 1)));
		given(tobuild.minPath("0_0", "1_1")).willReturn(Arrays.asList("0_0", "0_1", "1_1"));
		given(tobuild.getNodes()).willReturn(Arrays.asList("0_0", "0_1", "1_1"));
		List<String> expectedMinPath = Arrays.asList("0_0", "0_1", "1_1");
		assertEquals(gridService.getShortestPath("0_0", "1_1", 1), expectedMinPath);
		verify(impl, times(1)).getById(1);
		verify(tobuild, times(1)).minPath("0_0", "1_1");

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j] == 1) {

					verify(tobuild, times(1)).addNodes(i + "_" + j);

				}
			}
		}
		verify(spied, times(3)).isEnabled(0, 1);
		verify(spied, times(2)).isEnabled(1, 1);
		verify(spied, times(2)).isEnabled(0, 0);
		verify(spied, times(0)).isEnabled(2, 2);
		verify(spied, times(1)).isEnabled(eq(2), anyInt());
		verify(spied, times(2)).isEnabled(anyInt(), eq(2));
		verify(tobuild, times(1)).addEdge("0_0", "0_1");
		verify(tobuild, times(1)).addEdge("0_1", "1_1");
		verify(tobuild, times(1)).addEdge("1_1", "0_1");
		verify(tobuild, times(1)).addEdge("0_1", "0_0");

	}

	@Test
	public void getMinPathTestTwice() {
		given(impl.getById(0)).willReturn(new DatabaseGrid(new int[][] { { 1, 1 }, { 1, 1 } }, 0));
		gridService.getShortestPath("0_0", "0_0", 0);
		verify(tobuild, times(1)).removeAllNodes();

	}

	@Test
	public void getMinPathWhenPathIsEmptyTest() {
		DatabaseGrid spied;
		given(impl.getById(1)).willReturn(spied = Mockito.spy(new DatabaseGrid(1)));
		given(tobuild.minPath("0_0", "1_1")).willReturn(Arrays.asList(""));
		List<String> expectedMinPath = Arrays.asList("");
		assertEquals(gridService.getShortestPath("0_0", "1_1", 1), expectedMinPath);
		verify(impl, times(1)).getById(1);
		verify(tobuild, times(1)).minPath("0_0", "1_1");
		verify(tobuild, times(0)).addEdge("0_0", "0_1");
		verify(tobuild, times(0)).addEdge("0_1", "1_1");
		verify(spied, times(0)).isEnabled(anyInt(), anyInt());

	}

	@Test
	public void getMinPathWhenPathIsLengthOneTest() {
		DatabaseGrid spied;
		given(impl.getById(1)).willReturn(spied = Mockito.spy(new DatabaseGrid(1)));
		given(tobuild.minPath("0_0", "0_0")).willReturn(Arrays.asList("0_0"));
		List<String> expectedMinPath = Arrays.asList("0_0");
		assertEquals(gridService.getShortestPath("0_0", "0_0", 1), expectedMinPath);
		verify(impl, times(1)).getById(1);
		verify(tobuild, times(1)).minPath("0_0", "0_0");
		verify(tobuild, times(0)).addEdge("0_0", "0_0");
		verify(spied, times(0)).isEnabled(anyInt(), anyInt());
	}

	@Test
	public void getMinPathWhenMatrixIsAllZeroTest() {

		DatabaseGrid spied;
		spied = Mockito.spy(new DatabaseGrid(new int[][] { { 0, 0 }, { 0, 0 } }, 1));

		given(impl.getById(1)).willReturn(spied);
		List<String> path = gridService.getShortestPath("0_0", "1_0", 1);
		assertEquals(new LinkedList<>(), path);
		verify(impl, times(1)).getById(1);
		verify(tobuild, times(1)).minPath("0_0", "1_0");
		verify(tobuild, times(0)).addNodes(anyString());
		verify(tobuild, times(0)).addEdge(anyString(), anyString());
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				verify(spied, times(1)).isEnabled(i, j);

			}
		}
	}

}
