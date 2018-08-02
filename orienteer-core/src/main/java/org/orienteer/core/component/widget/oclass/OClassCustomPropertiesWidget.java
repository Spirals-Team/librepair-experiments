package org.orienteer.core.component.widget.oclass;

import java.util.Collection;

import org.apache.wicket.model.IModel;
import org.orienteer.core.component.widget.AbstractSchemaCustomPropertiesWidget;
import org.orienteer.core.widget.Widget;

import ru.ydn.wicket.wicketorientdb.model.OClassCustomModel;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Widget to show custom properties of an {@link OClass}
 */
@Widget(id="class-custom", domain="class", tab="configuration", order=30, autoEnable=true)
public class OClassCustomPropertiesWidget extends AbstractSchemaCustomPropertiesWidget<OClass> {

	public OClassCustomPropertiesWidget(String id, IModel<OClass> model,
			IModel<ODocument> widgetDocumentModel) {
		super(id, model, widgetDocumentModel);
	}

	@Override
	protected Collection<String> getOriginalCustomKeys() {
		return getModelObject().getCustomKeys();
	}

	@Override
	protected void addCustom(String key, String value) {
		getModelObject().setCustom(key, value);
	}

	@Override
	protected IModel<String> createCustomModel(
			IModel<OClass> schemaObjectModel, IModel<String> customPropertyModel) {
		return new OClassCustomModel(schemaObjectModel, customPropertyModel);
	}
	
	
	
}
