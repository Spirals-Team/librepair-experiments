package org.molgenis.data.decorator.temp;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import org.molgenis.data.Entity;
import org.molgenis.data.Repository;
import org.molgenis.data.decorator.DynamicRepositoryDecoratorFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Objects.requireNonNull;

@Component
public class PostFixingRepositoryDecoratorFactory implements DynamicRepositoryDecoratorFactory<Entity>
{
	private static final String ID = "postfix";
	private final Gson gson;

	public PostFixingRepositoryDecoratorFactory(Gson gson)
	{
		this.gson = requireNonNull(gson);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Repository createDecoratedRepository(Repository<Entity> repository, Map<String, Object> parameters)
	{
		return new PostFixingRepositoryDecorator(repository, parameters.get("attr").toString(),
				parameters.get("text").toString());
	}

	@Override
	public String getId()
	{
		return ID;
	}

	@Override
	public String getLabel()
	{
		return "postfix";
	}

	@Override
	public String getDescription()
	{
		return "This is a test decorator";
	}

	@Override
	public String getSchema()
	{
		return gson.toJson(of("title", "Postfixing Decorator", "type", "object", "properties",
				of("attr", of("type", "string", "description", "The attribute to increment"), "text",
						of("type", "string", "description", "The text to append")), "required",
				ImmutableList.of("attr", "text")));
	}
}
