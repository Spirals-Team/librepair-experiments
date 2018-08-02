package org.orienteer.core.component.widget.document.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.orienteer.core.component.BootstrapType;
import org.orienteer.core.component.FAIcon;
import org.orienteer.core.component.FAIconType;
import org.orienteer.core.component.command.AjaxCommand;
import org.orienteer.core.component.command.AjaxFormCommand;
import org.orienteer.core.widget.AbstractWidget;
import org.orienteer.core.widget.Widget;

import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * {@link Widget} for execution of a OFunction
 */
@Widget(id="function-executor", domain="document", order=20, autoEnable=true, selector="OFunction")
public class OFunctionExecuteWidget extends AbstractWidget<ODocument> {

	private ListView<String> args;
	private TextArea<String> result;
	
	public OFunctionExecuteWidget(String id, IModel<ODocument> model,
			IModel<ODocument> widgetDocumentModel) {
		super(id, model, widgetDocumentModel);
		Form<ODocument> form = new Form<ODocument>("form");
		args = new ListView<String>("args", new PropertyModel<List<String>>(model, "parameters")) {

			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new TextField<String>("arg", Model.of(""))
						.add(new AttributeModifier("placeholder", item.getModel())));
			}
		};
		args.setReuseItems(true);
		form.add(args);
		form.add(new AjaxFormCommand<ODocument>("execute", "widget.document.function-executor.execute") {
			@Override
			public void onClick(Optional<AjaxRequestTarget> targetOptional) {
				result.setModelObject(execute());
				targetOptional.ifPresent(target -> target.add(result));
			}
		}.setIcon(FAIconType.play).setBootstrapType(BootstrapType.PRIMARY));
		form.add(new AjaxCommand<ODocument>("refresh", "widget.document.function-executor.refresh") {
			@Override
			public void onClick(Optional<AjaxRequestTarget> targetOptional) {
				targetOptional.ifPresent(target -> target.add(OFunctionExecuteWidget.this));
			}
		}.setIcon(FAIconType.refresh).setBootstrapType(BootstrapType.WARNING));
		
		result = new TextArea<String>("result", Model.of(""));
		result.setOutputMarkupId(true);
		form.add(result);
		add(form);
	}
	
	protected String execute() {
		final Map<Object, Object> map = new HashMap<Object, Object>();
		final List<Object> values = new ArrayList<Object>();
		args.visitChildren(TextField.class, new IVisitor<TextField<String>, Void>() {

			@Override
			public void component(TextField<String> object, IVisit<Void> visit) {
				String value = object.getModelObject();
				String key = object.getParent().getDefaultModelObjectAsString();
				map.put(key, value);
				values.add(value);
			}
			
		});
		
		Object ret = null;
		try {
			ret = getDatabase().getMetadata().getFunctionLibrary()
					.getFunction((String)getModelObject().field("name")).execute(values.toArray());
		} catch (Exception e) {
			ret = Strings.toString(e);
		}
		if (ret==null){
			return null;
		}
		if (ret.getClass().isArray()){
			List<String> resultList = new ArrayList<String>(((OIdentifiable[]) ret).length);
			for (OIdentifiable object : (OIdentifiable[]) ret) {
				if (object instanceof ODocument){
					resultList.add(((ODocument) object).toJSON());
				}else{
					resultList.add(object.toString());
				}
			}
			return "[\n"+Strings.join(",\n", resultList)+"\n]";
		}else if (ret instanceof ODocument){
			return ((ODocument) ret).toJSON();
		}else{
			return ret.toString();
		}
	}
	
	@Override
	protected String getWidgetStyleClass() {
		return "strict";
	}

	@Override
	protected FAIcon newIcon(String id) {
		return new FAIcon(id, FAIconType.play);
	}

	@Override
	protected IModel<String> getDefaultTitleModel() {
		return new ResourceModel("widget.document.function-executor");
	}

}
