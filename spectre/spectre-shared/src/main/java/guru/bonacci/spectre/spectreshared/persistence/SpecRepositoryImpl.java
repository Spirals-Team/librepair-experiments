package guru.bonacci.spectre.spectreshared.persistence;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;

public class SpecRepositoryImpl implements SpecRepositoryCustom {
	
	public static final String INDEX = "logstash-spectre";
	public static final String TYPE = "logs";

	@Autowired
	private ElasticsearchTemplate template;

	public void addData(String key, Object nestedObject, Spec s) throws IOException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(INDEX);
		updateRequest.type(TYPE);
		updateRequest.id(s.id);
		updateRequest.doc(jsonBuilder().startObject().field(key, nestedObject).endObject());
		UpdateQuery updateQuery = new UpdateQueryBuilder().withId(s.id)
														  .withClass(Spec.class)
														  .withUpdateRequest(updateRequest)
														  .build();
		template.update(updateQuery);
	}
}
