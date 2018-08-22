package org.molgenis.data.decorator;

import com.google.gson.Gson;
import org.mockito.Mock;
import org.molgenis.data.*;
import org.molgenis.data.decorator.meta.*;
import org.molgenis.data.event.BootstrappingEvent;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.data.support.QueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.molgenis.data.decorator.meta.DecoratorConfigurationMetadata.DECORATOR_CONFIGURATION;
import static org.molgenis.data.decorator.meta.DecoratorConfigurationMetadata.ENTITY_TYPE_ID;
import static org.molgenis.data.event.BootstrappingEvent.BootstrappingStatus.FINISHED;
import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = { DecoratorConfigurationMetadata.class, DecoratorPackage.class,
		DynamicDecoratorMetadata.class })
public class DynamicRepositoryDecoratorRegistryImplTest extends AbstractMolgenisSpringTest
{
	@Autowired
	DecoratorConfigurationMetadata decoratorConfigurationMetadata;

	@Mock
	private Repository<Entity> repository;
	@Mock
	private DataService dataService;
	@Mock
	private EntityType entityType;
	@Mock
	private DecoratorConfiguration decoratorConfiguration;
	@Mock
	private DynamicRepositoryDecoratorFactory<Entity> dynamicRepositoryDecoratorFactory;

	@BeforeMethod
	public void setUp()
	{
		when(entityType.getId()).thenReturn("entityTypeId");
		when(repository.getEntityType()).thenReturn(entityType);
	}

	@Test
	public void testDecorate()
	{
		DynamicDecorator dynamicDecorator = mock(DynamicDecorator.class);
		@SuppressWarnings("unchecked")
		Repository<Entity> decoratedRepository = mock(Repository.class);

		when(decoratedRepository.getName()).thenReturn("decoratedRepositoryName");
		//		when(decoratorConfiguration.getDecoratorParameters()).thenReturn(Stream.of(dynamicDecorator)); //TODO
		when(dynamicDecorator.getId()).thenReturn("dynamicDecoratorId");
		when(dynamicRepositoryDecoratorFactory.getId()).thenReturn("dynamicDecoratorId");
		when(dynamicRepositoryDecoratorFactory.createDecoratedRepository(repository, mock(Map.class))).thenReturn(
				decoratedRepository);
		Query<DecoratorConfiguration> query = new QueryImpl<>();
		query.eq(ENTITY_TYPE_ID, "entityTypeId");
		when(dataService.findOne(DECORATOR_CONFIGURATION, query, DecoratorConfiguration.class)).thenReturn(
				decoratorConfiguration);

		DynamicRepositoryDecoratorRegistryImpl dynamicRepositoryDecoratorRegistry = new DynamicRepositoryDecoratorRegistryImpl(
				dataService, mock(Gson.class));
		//fake the event to tell the registry that bootstrapping is done.
		dynamicRepositoryDecoratorRegistry.onApplicationEvent(new BootstrappingEvent(FINISHED));

		dynamicRepositoryDecoratorRegistry.addFactory(dynamicRepositoryDecoratorFactory);

		assertEquals(dynamicRepositoryDecoratorRegistry.decorate(repository).getName(), "decoratedRepositoryName");
	}

	@Test
	public void testDecorateNoDecorator()
	{
		when(repository.getEntityType()).thenReturn(entityType);
		when(repository.getName()).thenReturn("repositoryName");
		when(entityType.getId()).thenReturn("entityTypeId");

		DynamicRepositoryDecoratorRegistryImpl dynamicRepositoryDecoratorRegistry = new DynamicRepositoryDecoratorRegistryImpl(
				dataService, mock(Gson.class));
		//fake the event to tell the registry that bootstrapping is done.
		dynamicRepositoryDecoratorRegistry.onApplicationEvent(new BootstrappingEvent(FINISHED));

		assertEquals(dynamicRepositoryDecoratorRegistry.decorate(repository).getName(), "repositoryName");
	}
}