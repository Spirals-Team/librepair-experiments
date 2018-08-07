package guru.bonacci.oogway.oracle.service.persistence;

import static org.springframework.data.elasticsearch.annotations.FieldType.keyword;
import static org.springframework.data.elasticsearch.annotations.FieldType.text;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A gem is a cut and polished precious stone or pearl fine enough for
 * use in jewelry. In this context: wisdom is a gem of infinite value.
 */
@Data
@NoArgsConstructor
@ToString(exclude="id")
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
}
