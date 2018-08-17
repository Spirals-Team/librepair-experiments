package com.tommasopuccetti.server;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

	@Autowired
	IMongoRepository repo;

	public Media findOneById(String id) {
		
		return repo.findOne(id);
		
	}

	public List<Media> findAllMedia() {

		return repo.findAll();
	}

	public void deleteOneById(String id) {
		
		repo.delete(id);

	}

	public void deleteAll() {
		
		repo.deleteAll();

	}

}
