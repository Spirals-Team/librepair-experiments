package org.orienteer.core.component.property;

import com.google.inject.Inject;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.orienteer.core.component.BootstrapSize;
import org.orienteer.core.component.BootstrapType;
import org.orienteer.core.component.command.AjaxFormCommand;
import org.orienteer.core.component.command.SelectODocumentCommand;
import org.orienteer.core.component.table.OEntityColumn;
import org.orienteer.core.component.table.OrienteerDataTable;
import org.orienteer.core.component.table.component.GenericTablePanel;
import org.orienteer.core.service.IOClassIntrospector;
import ru.ydn.wicket.wicketorientdb.model.DynamicPropertyValueModel;
import ru.ydn.wicket.wicketorientdb.model.OPropertyModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * {@link GenericPanel} to edit list or set of links
 *
 * @param <T> the type of {@link OIdentifiable} - commonly {@link ODocument}
 * @param <M> the type of a collection
 */
public class LinksCollectionEditPanel<T extends OIdentifiable, M extends Collection<T>> extends GenericPanel<M>
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private IOClassIntrospector oClassIntrospector;

	public LinksCollectionEditPanel(String id, final IModel<ODocument> documentModel, OProperty property) {
		super(id, new DynamicPropertyValueModel<M>(documentModel, new OPropertyModel(property)));
		
		ISortableDataProvider<ODocument, String> provider = oClassIntrospector.prepareDataProviderForProperty(property, documentModel);
		final String propertyName = property.getName();
		
		List<IColumn<ODocument, String>> columns = new ArrayList<IColumn<ODocument,String>>();
		columns.add(new OEntityColumn(property.getLinkedClass(), DisplayMode.VIEW.asModel()));
		columns.add(new AbstractColumn<ODocument, String>(null) {

			@Override
			public void populateItem(Item<ICellPopulator<ODocument>> cellItem,
					String componentId, final IModel<ODocument> rowModel) {
				
				cellItem.add(new AjaxFormCommand<Object>(componentId, new ResourceModel("command.release"))
						{
							{
								setBootstrapType(BootstrapType.WARNING);
								setBootstrapSize(BootstrapSize.EXTRA_SMALL);
							}

							@Override
							public void onClick(Optional<AjaxRequestTarget> targetOptional) {
								ODocument doc = documentModel.getObject();
								Collection<ODocument> values = doc.field(propertyName);
								if(values!=null)
								{
									values.remove(rowModel.getObject());
								}
								doc.save();
							}
					
						});
			}
		});
		GenericTablePanel<ODocument> tablePanel = new GenericTablePanel<>("links", columns, provider, 10);
		OrienteerDataTable<ODocument, String> table = tablePanel.getDataTable();
		table.getHeadersToolbar().setVisibilityAllowed(false);
		table.getNoRecordsToolbar().setVisibilityAllowed(false);
		table.addCommand(new SelectODocumentCommand(table, documentModel, new OPropertyModel(property))
				.setBootstrapSize(BootstrapSize.EXTRA_SMALL)
				.setIcon((String)null));
		add(tablePanel);
	}
}
