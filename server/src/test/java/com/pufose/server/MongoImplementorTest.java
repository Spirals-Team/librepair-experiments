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
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MongoImplementorTest {
	@Mock
	private IMongoRepository gridRepository;
	
	@InjectMocks
	private MongoImplementor  implementor;
	
	
	@Test
	public void getAllIdWhenThereIsOneGridTest() {
		
		DatabaseGrid grid1 = new DatabaseGrid(1);
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
		DatabaseGrid grid1 = new DatabaseGrid(1);
		DatabaseGrid grid2 = new DatabaseGrid(2);
		given(gridRepository.findAll()).willReturn(Arrays.asList(grid1, grid2));
		assertThat(implementor.getAllId()).isEqualTo(Arrays.asList("1", "2"));
		verify(gridRepository, times(1)).findAll();
	}
	
	@Test
	public void getByIdWhenThereIsNoSuchGridTest() {
	
		assertNull(implementor.getById(7));
		verify(gridRepository, times(1)).findById(7);

	}
	
	@Test
	public void getByIdWhenThereIsSuchGridTest() {
		DatabaseGrid grid1 = new DatabaseGrid(1);
		given(gridRepository.findById(1)).willReturn(grid1);
		assertThat(implementor.getById(1)).isEqualTo(grid1);
		verify(gridRepository, times(1)).findById(1);

	}
	
	@Test
	public void nextIdWhenThereAreGridsTest() {
		DatabaseGrid grid1 = new DatabaseGrid(1);
		DatabaseGrid grid2 = new DatabaseGrid(2);
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
		
		implementor.dropTable(1);
		verify(gridRepository, times(1)).delete(1);

	}
	
	@Test
	public void saveInDbIsCalledTest() {
		DatabaseGrid grid;
		implementor.storeInDb(grid=new DatabaseGrid(0));
		verify(gridRepository,times(1)).save(grid);

	}
	
	@Test
	public void getAllGridsWhenThereAreNoGridsTest() {
		List<DatabaseGrid> grids = new ArrayList<>();
		given(gridRepository.findAll()).willReturn(new ArrayList<>());
		assertThat(implementor.getAllGrids()).isEqualTo(grids);
		verify(gridRepository, times(1)).findAll();

	}
	
	@Test
	public void getAllGridsWhenThereAreGridsTest() {
		List<DatabaseGrid> expectedgrids=Arrays.asList(new DatabaseGrid(1),new DatabaseGrid(2));
		given(gridRepository.findAll()).willReturn
			(expectedgrids);
		List<DatabaseGrid> actualgrids=implementor.getAllGrids();
		
		assertEquals(actualgrids,expectedgrids);
		verify(gridRepository,times(1)).findAll();
		

	}

}
