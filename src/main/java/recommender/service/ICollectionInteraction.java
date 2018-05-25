package recommender.service;

import java.io.File;
import java.io.FileNotFoundException;

import recommender.model.ClassCollection;

public interface ICollectionInteraction {

	public ClassCollection parseClassfile(File file) throws FileNotFoundException;
}
