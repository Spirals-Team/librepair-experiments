package guru.bonacci.oogway.utilities;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collector;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class CustomFileUtils {

	public static String readToString(String fileName) throws IOException {
		Resource resource = new ClassPathResource(fileName);
		return read(resource.getInputStream(), joining("\n"));
	}	
		
	public static List<String> readToList(String fileName) throws IOException {
		Resource resource = new ClassPathResource(fileName);
		return read(resource.getInputStream(), toList());
	}	

	private static <T> T read(InputStream input, Collector<? super String, ?, T> collector) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
        	return buffer.lines().collect(collector);
        }
    }
}
