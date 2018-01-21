package com.pufose.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MysqlImplementorTest {
	@Mock
	private IMySqlRepository gridRepository;
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	private MySqlImplementor  implementor;
	
	
	@Test
	public void getAllIdWhenThereIsOneGridTest() {
		
		SqlGrid grid1 = new SqlGrid(1);
		given(gridRepository.findAll()).willReturn(Arrays.asList(grid1));
		assertThat(implementor.getAllId()).isEqualTo(Arrays.asList("1"));
		verify(gridRepository, times(1)).findAll();
		
	}
	
	@Test
	public void getAllIdWhenThereIsNoGridTest() {
		
		given(gridRepository.findAll()).willReturn(Arrays.asList());
		assertThat(implementor.getAllId()).isEqualTo(new ArrayList<>());
		verify(gridRepository, times(1)).findAll();

	}
	
	@Test
	public void getAllIdWhenThereIsMoreThanOneGridTest() {
		SqlGrid grid1 = new SqlGrid(1);
		SqlGrid grid2 = new SqlGrid(2);
		given(gridRepository.findAll()).willReturn(Arrays.asList(grid1, grid2));
		assertThat(implementor.getAllId()).isEqualTo(Arrays.asList("1", "2"));
		verify(gridRepository, times(1)).findAll();
	}
	
	@Test
	public void getByIdWhenThereIsNoSuchGridTest() {
		
		assertNull(implementor.getById(7));
		verify(gridRepository, times(1)).findOne(new Long(7));

	}
	
	@Test
	public void getByIdWhenThereIsSuchGridTest() {
		SqlGrid grid1 = new SqlGrid(1);
		given(gridRepository.findOne(new Long(1))).willReturn(grid1);
		assertThat(implementor.getById(1)).isEqualTo(grid1.toDatabase());
		verify(gridRepository, times(1)).findOne(new Long(1));

	}
	
	@Test
	public void nextIdWhenThereAreGridsTest() {
		SqlGrid grid1 = new SqlGrid(1);
		SqlGrid grid2 = new SqlGrid(2);
		given(gridRepository.findAll()).willReturn(Arrays.asList(grid1, grid2));
		assertThat(implementor.nextId()).isEqualTo(3);
		verify(gridRepository, times(1)).findAll();
	}

	@Test
	public void nextIdWhenThereIsNoGridTest() {
		given(gridRepository.findAll()).willReturn(new ArrayList<>());
		assertThat(implementor.nextId()).isEqualTo(1);
		verify(gridRepository, times(1)).findAll();

	}
	
	
	@Test
	public void dropTableDeleteIsCalledTest() {
		
		implementor.dropTable(0);
		String EXPECTED_QUERY="DELETE FROM sql_grid WHERE id = ?";
		verify(jdbcTemplate,times(1)).update(EXPECTED_QUERY,0);

	}
	
	@Test
	public void saveInDbIsCalledTest() {
		int[][] matrix= new int[][] {
			{1,1,1},
			{1,1,0},
			{1,0,0}
		};
		implementor.storeInDb(new DatabaseGrid(matrix,0));
		String expectedMatrix="111110100";
		String EXPECTED_QUERY="INSERT INTO sql_grid VALUES (?, ?, ?)";
		verify(jdbcTemplate,times(1)).update(EXPECTED_QUERY,0,expectedMatrix,3);

	}
	
	@Test
	public void getAllGridsWhenThereAreNoGridsTest() {
		List<SqlGrid> grids = new ArrayList<>();
		given(gridRepository.findAll()).willReturn(new ArrayList<>());
		assertThat(implementor.getAllGrids()).isEqualTo(grids);
		verify(gridRepository, times(1)).findAll();

	}
	
	@Test
	public void getAllGridsWhenThereAreGridsTest() {
		List<SqlGrid> sqllist=Arrays.asList(new SqlGrid(1),new SqlGrid(2));
		given(gridRepository.findAll()).willReturn
			(sqllist);
		List<DatabaseGrid> actualgrids=implementor.getAllGrids();
		List<DatabaseGrid> expectedgrids= new ArrayList<>();
		sqllist.forEach(g-> expectedgrids.add(g.toDatabase()));
		assertEquals(actualgrids,expectedgrids);
		verify(gridRepository,times(1)).findAll();
		

	}

}
