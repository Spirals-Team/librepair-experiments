package org.orienteer.pages.module;

import com.google.inject.Singleton;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.orienteer.core.OrienteerWebApplication;
import org.orienteer.core.module.AbstractOrienteerModule;
import org.orienteer.core.util.OSchemaHelper;
import org.orienteer.pages.PagesCompoundRequestMapper;

/**
 * {@link AbstractOrienteerModule} to provide extentions for Orienteer Pages
 */
@Singleton
public class PagesModule extends AbstractOrienteerModule {
	
	public static final String NAME = "pages";
	public static final String OCLASS_PAGE="OPage";
	public static final String OPROPERTY_TITLE="title";
	public static final String OPROPERTY_DESCRIPTION="description";
	public static final String OPROPERTY_CONTENT="content";
	public static final String OPROPERTY_SCRIPT="script";
	public static final String OPROPERTY_PATH="path";
	public static final String OPROPERTY_EMBEDDED="embedded";
	public static final String OPROPERTY_DOCUMENT="document";
	
	private PagesCompoundRequestMapper pagesCompoundRequestMapper;

	protected PagesModule() {
		super(NAME, 1);

	}
	
	@Override
	public ODocument onInstall(OrienteerWebApplication app, ODatabaseDocument db) {
		onUpdate(app, db, 0, getVersion());
		return null;
	}

	@Override
	public void onUpdate(OrienteerWebApplication app, ODatabaseDocument db,
			int oldVersion, int newVersion) {
		if(oldVersion>=newVersion) return;
		switch (oldVersion+1)
		{
			case 1:
				onUpdateToFirstVesion(app, db);
				break;
			default:
				break;
		}
		if(oldVersion+1<newVersion) onUpdate(app, db, oldVersion + 1, newVersion);
	}

	public void onUpdateToFirstVesion(OrienteerWebApplication app, ODatabaseDocument db)
	{
		OSchemaHelper helper = OSchemaHelper.bind(db);
		helper.oClass(OCLASS_PAGE)
				.oProperty(OPROPERTY_TITLE, OType.STRING, 0).markAsDocumentName()
				.oProperty(OPROPERTY_PATH, OType.STRING, 10)
				.oProperty(OPROPERTY_DESCRIPTION, OType.STRING, 20).assignVisualization("textarea")
				.oProperty(OPROPERTY_CONTENT, OType.STRING, 30).assignVisualization("textarea")
				.oProperty(OPROPERTY_SCRIPT, OType.STRING, 40).assignVisualization("textarea")
				.oProperty(OPROPERTY_EMBEDDED, OType.BOOLEAN, 50)
				.oProperty(OPROPERTY_DOCUMENT, OType.LINK, 60);
	}
	
	@Override
	public void onInitialize(OrienteerWebApplication app, ODatabaseDocument db) {
		super.onInitialize(app, db);
		app.registerWidgets("org.orienteer.pages.component.widget");
		app.mount(pagesCompoundRequestMapper = new PagesCompoundRequestMapper());
		app.getOrientDbSettings().getORecordHooks().add(PagesHook.class);
	}
	
	@Override
	public void onDestroy(OrienteerWebApplication app, ODatabaseDocument db) {
		app.unregisterWidgets("org.orienteer.pages.component.widget");
		app.getRootRequestMapperAsCompound().remove(pagesCompoundRequestMapper);
		app.getOrientDbSettings().getORecordHooks().remove(PagesHook.class);
	}
	
	public PagesCompoundRequestMapper getPagesCompoundRequestMapper() {
		return pagesCompoundRequestMapper;
	}

}
