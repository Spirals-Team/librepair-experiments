package org.orienteer.core.component.meta;

import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

/**
 * {@link GenericPanel} that aware about: entity, property and a value
 *
 * @param <E> the type of an entity
 * @param <P> the type of a property
 * @param <V> the type of a value
 */
public abstract class AbstractEntityAndPropertyAwarePanel<E, P, V> extends GenericPanel<V> implements IEntityAndPropertyAware<E, P, V>
{
	private static final long serialVersionUID = 1L;
	private IModel<E> entityModel;
	private IModel<P> propertyModel;
	
	public AbstractEntityAndPropertyAwarePanel(String id, IModel<E> entityModel, IModel<P> propertyModel, IModel<V> valueModel)
	{
		super(id, valueModel);
		this.entityModel = entityModel;
		this.propertyModel = propertyModel;
	}
	
	public AbstractEntityAndPropertyAwarePanel(String id, IModel<E> entityModel, IModel<P> propertyModel)
	{
		super(id);
		this.entityModel = entityModel;
		this.propertyModel = propertyModel;
		setModel(resolveValueModel());
	}
	
	protected abstract IModel<V> resolveValueModel();
	
	@Override
	public IModel<E> getEntityModel()
	{
		return entityModel;
	}
	
	@Override
	public IModel<P> getPropertyModel()
	{
		return propertyModel;
	}
	
	@Override
	public IModel<V> getValueModel()
	{
		return getModel();
	}
	
	@Override
	public E getEntityObject()
	{
		return getEntityModel().getObject();
	}
	
	@Override
	public P getPropertyObject()
	{
		return getPropertyModel().getObject();
	}
	
	@Override
	public V getValueObject()
	{
		return getValueModel().getObject();
	}
	
	@Override
	public void detachModels() {
		super.detachModels();
		if(entityModel!=null) entityModel.detach();
		if(propertyModel!=null) propertyModel.detach();
	}

}
