package org.orienteer.core.component.command.modal;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.orienteer.core.service.IUmlService;

import com.google.inject.Inject;
import com.orientechnologies.orient.core.metadata.schema.OClass;

/**
 * Modal window to display UML for a selected {@link OClass}es
 */
public class ViewUMLDialogPanel  extends GenericPanel<String>
{
	@Inject
	private IUmlService umlService;

	public ViewUMLDialogPanel(String id, final IModel<String> model)
	{
		super(id, model);
		add(new WebMarkupContainer("umlImg")
			{

				@Override
				protected void onComponentTag(ComponentTag tag) {
					super.onComponentTag(tag);
					tag.put("src", umlService.asImage(model.getObject()));
				}
						
			});
		add(new MultiLineLabel("uml", model).setVisible(umlService.isUmlDebugEnabled()));
	}
	
}
