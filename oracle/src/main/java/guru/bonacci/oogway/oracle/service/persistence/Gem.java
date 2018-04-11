package guru.bonacci.oogway.oracle.service.persistence;

import static java.lang.String.format;
import static org.springframework.data.elasticsearch.annotations.FieldType.keyword;
import static org.springframework.data.elasticsearch.annotations.FieldType.text;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A gem is a cut and polished precious stone or pearl fine enough for
 * use in jewelry. In this context: wisdom is a gem of infinite value.
 */
@Document(indexName = "oracle", type = "quote", shards = 1, replicas = 0, refreshInterval = "-1")
public class Gem {

	public static final String SAYING = "saying";
	public static final String AUTHOR = "author";

	@Id
	@JsonIgnore
	private String id;

	@Field(type = text, store = true, analyzer = "english", searchAnalyzer = "english")
	private String saying;

	@Field(type = keyword)
	private String author;

	public Gem() {
	}

	/**
	 * We don't want to have multiple ES-documents for the same quotes. In
	 * ElasticSearch there is no concept of uniqueness on fields. Exception is
	 * the _id field. When the _id field is equal ES will update the field. The
	 * ES documentation states that full-text search on an _id field is
	 * possible. Testing proves this wrong. Therefore, as a (temporary) solution
	 * we persist the quote in the _id field to allow uniqueness and in the
	 * saying-field for full-text search
	 */
	public Gem(String saying) {
		this.id = saying;
		this.saying = saying;
	}

	public Gem(String saying, String author) {
		this(saying);
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSaying() {
		return saying;
	}

	public void setSaying(String saying) {
		this.saying = saying;
	}

	/*
	 * I had a dream... 
	 * 
	 * What is said to work for hibernate/jpa does not work for spring-data-elasticsearch: return Optional<String>
	 * 
	 * 'If we are using field-based access persistence, 
	 * then the underlying entity attribute can be mapped using the actual persisted type, 
	 * while the getter method can use an Optional instead.'
	 * 
	 * ES demands the instance variable and its corresponding getter to be of both the same name AND the same type :(
	 * 
	 * Then the GemMapper would include a custom mapper for optional strings to the normal strings equivalent in the dto.
	 */
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
    public String toString() {
        return format("Gem[saying='%s', author='%s']", saying, author);
    }
	
	@Override
	public boolean equals(Object o) {
	    return EqualsBuilder.reflectionEquals(this, o);
	}
	
	@Override
	public int hashCode() {
	    return HashCodeBuilder.reflectionHashCode(this);
	}
}
