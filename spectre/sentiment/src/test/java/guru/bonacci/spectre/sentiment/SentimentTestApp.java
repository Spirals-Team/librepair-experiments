package guru.bonacci.spectre.sentiment;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.spectre.sentiment.SentimentServer;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { SentimentServer.class }))
public class SentimentTestApp {

	public static void main(String[] args) {
		SpringApplication.run(SentimentTestApp.class, args);
	}
	
	@Bean
	public StanfordCoreNLP sentimentPipeline() {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		return new StanfordCoreNLP(props);
	}
}