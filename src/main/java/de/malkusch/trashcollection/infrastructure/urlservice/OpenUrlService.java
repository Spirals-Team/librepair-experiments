package de.malkusch.trashcollection.infrastructure.urlservice;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public interface OpenUrlService {

	@Retryable(IOException.class)
	InputStream open(URL url) throws IOException;

}
