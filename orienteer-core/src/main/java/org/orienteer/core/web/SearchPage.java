package org.orienteer.core.web;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.java.util.function.serializable.SerializableSupplier;
import org.orienteer.core.MountPath;
import org.orienteer.core.OrienteerWebApplication;
import org.orienteer.core.component.OClassSearchPanel;
import org.orienteer.core.component.command.EditODocumentsCommand;
import org.orienteer.core.component.command.SaveODocumentsCommand;
import org.orienteer.core.component.property.DisplayMode;
import org.orienteer.core.component.table.OrienteerDataTable;
import org.orienteer.core.service.IFilterPredicateFactory;
import ru.ydn.wicket.wicketorientdb.security.OSecurityHelper;
import ru.ydn.wicket.wicketorientdb.security.OrientPermission;
import ru.ydn.wicket.wicketorientdb.security.RequiredOrientResource;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Page to search and display search results
 */
@MountPath("/search")
@RequiredOrientResource(value = OSecurityHelper.FEATURE, specific=SearchPage.SEARCH_FEATURE, permissions=OrientPermission.READ)
public class SearchPage extends OrienteerBasePage<String> {

	public static final String SEARCH_FEATURE = "search";
	
	public SearchPage() {
		super(Model.of(""));
	}

	public SearchPage(IModel<String> model) {
		super(model);
	}

	public SearchPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected IModel<String> resolveByPageParameters(PageParameters params) {
		String query = params.get("q").toOptionalString();
		return Model.of(query);
	}

	@Override
	public void initialize() {
		super.initialize();
		add(new OClassSearchPanel("searchPanel", getModel(), createClassesGetter()) {
			@Override
			protected void onPrepareResults(OrienteerDataTable<ODocument, String> table, OClass oClass, IModel<DisplayMode> modeModel) {
				table.addCommand(new EditODocumentsCommand(table, modeModel, oClass));
				table.addCommand(new SaveODocumentsCommand(table, modeModel));
			}
		});
	}

	private SerializableSupplier<List<OClass>> createClassesGetter() {
		Predicate<OClass> predicate = OrienteerWebApplication.get().getServiceInstance(IFilterPredicateFactory.class)
				.getPredicateForClassesSearch();
		return () -> OClassSearchPanel.CLASSES_ORDERING.sortedCopy(getDatabase().getMetadata().getSchema().getClasses())
				.stream()
				.filter(predicate)
				.sorted()
				.collect(Collectors.toList());
	}

	@Override
	public IModel<String> getTitleModel() {
		return new ResourceModel("search.title");
	}

}
