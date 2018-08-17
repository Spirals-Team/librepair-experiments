package com.tommasopuccetti.server;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoRepositoryIT {

	@Autowired
	IMongoRepository repo;

	@Autowired
	MongoOperations mongoops;

	@Before
	public void setUp() {
		mongoops.dropCollection(Media.class);
	}

	@Test
	public void testSave() {
		Media tosave = new Media("title", "embeddedurlcode");
		repo.save(tosave);
		List<Media> allMedia = mongoops.findAll(Media.class);
		assertEquals(1, allMedia.size());
		assertEquals("title", allMedia.get(0).getName());
		assertEquals("embeddedurlcode", allMedia.get(0).getEmbedded());
		assertNotNull(allMedia.get(0).getId());
		
	}
	
	@Test
	public void testFindOne() {
		Media tosave = new Media("title", "embeddedurlcode");
		tosave.setId("genereted_id");
		mongoops.save(tosave);
		Media found = repo.findOne("genereted_id");
		assertEquals("embeddedurlcode", found.getEmbedded());
		assertEquals("title", found.getName());
		assertEquals("embeddedurlcode", found.getEmbedded());
		
	}
	
	@Test
	public void testFindAll() {
		Media media1 = new Media("title1", "embeddedurlcode1");
		Media media2 = new Media("title2", "embeddedurlcode2");
		media1.setId("id1");
		media2.setId("id2");
		mongoops.save(media1);
		mongoops.save(media2);
		List<Media> found = repo.findAll();
		assertEquals(2, found.size());
		assertEquals("id1", found.get(0).getId());
		assertEquals("id2", found.get(1).getId());
		assertEquals("title1", found.get(0).getName());
		assertEquals("title2", found.get(1).getName());
		assertEquals("embeddedurlcode1", found.get(0).getEmbedded());
		assertEquals("embeddedurlcode2", found.get(1).getEmbedded());
	}
	
	@Test
	public void testDelete() {
		Media tosave = new Media("title", "embeddedurlcode");
		tosave.setId("generated_id");
		mongoops.save(tosave);
		repo.delete("generated_id");
		Media found = mongoops.findById("generated_id",Media.class);
		assertEquals(null, found);
		
	}
	
	@Test
	public void testDeleteAll() {
		Media media1 = new Media("title1", "embeddedurlcode1");
		Media media2 = new Media("title2", "embeddedurlcode2");
		media1.setId("id1");
		media2.setId("id2");
		mongoops.save(media1);
		mongoops.save(media2);
		repo.deleteAll();
		List<Media> found = mongoops.findAll(Media.class);
		assertEquals(0, found.size());
		
	}
	
	

}
