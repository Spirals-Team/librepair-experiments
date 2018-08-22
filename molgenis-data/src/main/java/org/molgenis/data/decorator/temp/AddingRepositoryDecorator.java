package org.molgenis.data.decorator.temp;

import org.molgenis.data.AbstractRepositoryDecorator;
import org.molgenis.data.Entity;
import org.molgenis.data.Repository;

//Decorator specifically for DynamicDecoratorIT
public class AddingRepositoryDecorator extends AbstractRepositoryDecorator<Entity>
{
	private final String attributeName;

	public AddingRepositoryDecorator(Repository<Entity> delegateRepository, String attributeName)
	{
		super(delegateRepository);
		this.attributeName = attributeName;
	}

	@Override
	public void update(Entity entity)
	{
		entity.set(attributeName, entity.getInt(attributeName) + 1);
		super.update(entity);
	}
}