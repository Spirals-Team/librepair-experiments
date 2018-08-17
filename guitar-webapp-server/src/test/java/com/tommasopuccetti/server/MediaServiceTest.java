package com.tommasopuccetti.server;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MediaServiceTest {

	@Mock
	private IMongoRepository repo;

	@InjectMocks
	private MediaService service;

	@Test
	public void testFindOneByIdWhenFound() {
		when(repo.findOne("test_id")).thenReturn(new Media("title", "embeddedurlcode"));
		Media found = service.findOneById("test_id");
		verify(repo, times(1)).findOne("test_id");
		assertEquals("title", found.getName());
		assertEquals("embeddedurlcode", found.getEmbedded());

	}

	@Test
	public void testFindOneByIdNotFound() {
		when(repo.findOne("test_id")).thenReturn(null);
		assertNull(service.findOneById("test_id"));
		verify(repo, times(1)).findOne("test_id");

	}

	@Test
	public void testFindAllMediaInDbWhenThereAreNoMedia() {
		when(repo.findAll()).thenReturn(Arrays.asList());
		List<Media> found = service.findAllMedia();
		verify(repo, times(1)).findAll();
		assertEquals(0, found.size());

	}

	@Test
	public void testFindAllMediaWhenThereIsOneMedia() {
		when(repo.findAll()).thenReturn(Arrays.asList(new Media("title", "embeddedurlcode")));
		List<Media> found = service.findAllMedia();
		verify(repo, times(1)).findAll();
		assertEquals(1, found.size());
		Media unique = found.get(0);
		assertEquals("title", unique.getName());
		assertEquals("embeddedurlcode", unique.getEmbedded());

	}

	@Test
	public void testFindAllMediaWhenThereIsMoreThanOneMedia() {
		Media media1 = new Media("title1", "embeddedurlcode1");
		Media media2 = new Media("title2", "embeddedurlcode2");
		when(repo.findAll()).thenReturn(Arrays.asList(media1, media2 ));
		List<Media> found = service.findAllMedia();
		assertThat(found).containsExactly(media1, media2);
		verify(repo, times(1)).findAll();
		assertEquals(2, found.size());

	}
	
	@Test
	public void testDeleteById() {
		service.deleteOneById("test_id");
		verify(repo, times(1)).delete("test_id");
	}
	
	@Test
	public void testDeleteAll() {
		service.deleteAll();
		verify(repo, times(1)).deleteAll();
		
	}
	
	
		
}
