package recommender.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import recommender.model.ClassCollection;
import recommender.service.ICollectionInteraction;

public class CollectionInteraction implements ICollectionInteraction{

	
	public ClassCollection parseClassfile(File file) throws FileNotFoundException {
		
		Gson gson = new GsonBuilder().create();
		
		JsonReader reader = new JsonReader(new FileReader(file));
		ClassCollection classCollection = gson.fromJson(reader,ClassCollection.class);

		return classCollection;	
	}
}
