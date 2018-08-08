package org.orienteer.core.component.command;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.orienteer.core.component.table.DataTableCommandsToolbar;
import org.orienteer.core.component.table.OrienteerDataTable;

import ru.ydn.wicket.wicketorientdb.security.OSecurityHelper;
import ru.ydn.wicket.wicketorientdb.security.OrientPermission;
import ru.ydn.wicket.wicketorientdb.security.RequiredOrientResource;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.security.ODatabaseSecurityResources;
import com.orientechnologies.orient.core.metadata.security.ORule;

/**
 * {@link Command} to delete {@link OProperty}
 */
@RequiredOrientResource(value = OSecurityHelper.SCHEMA, permissions=OrientPermission.DELETE)
public class DeleteOPropertyCommand extends AbstractDeleteCommand<OProperty> {

	public DeleteOPropertyCommand(OrienteerDataTable<OProperty, ?> table) {
		super(table);
	}
	
	@Override
	protected void performMultiAction(AjaxRequestTarget target, List<OProperty> objects) {
		getDatabase().commit();
		super.performMultiAction(target, objects);
		getDatabase().begin();
	}

	@Override
	protected void perfromSingleAction(AjaxRequestTarget target, OProperty object) {
		object.getOwnerClass().dropProperty(object.getName());
	}

}
