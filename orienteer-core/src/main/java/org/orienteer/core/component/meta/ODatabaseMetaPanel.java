package org.orienteer.core.component.meta;

import com.orientechnologies.orient.core.config.OStorageEntryConfiguration;
import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.clusterselection.OBalancedClusterSelectionStrategy;
import com.orientechnologies.orient.core.metadata.schema.clusterselection.ODefaultClusterSelectionStrategy;
import com.orientechnologies.orient.core.metadata.schema.clusterselection.ORoundRobinClusterSelectionStrategy;
import com.orientechnologies.orient.core.metadata.security.ORule;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.orienteer.core.component.property.BooleanEditPanel;
import org.orienteer.core.component.property.BooleanViewPanel;
import org.orienteer.core.component.property.DisplayMode;

import ru.ydn.wicket.wicketorientdb.OrientDbWebSession;
import ru.ydn.wicket.wicketorientdb.model.SimpleNamingModel;
import ru.ydn.wicket.wicketorientdb.security.OSecurityHelper;
import ru.ydn.wicket.wicketorientdb.security.OrientPermission;
import ru.ydn.wicket.wicketorientdb.validation.DateFormatValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import static com.orientechnologies.orient.core.db.ODatabase.ATTRIBUTES;
import static com.orientechnologies.orient.core.db.ODatabase.STATUS;

/**
 * Meta panel for {@link ODatabase}
 *
 * @param <V> type of a value
 */
public class ODatabaseMetaPanel<V> extends AbstractComplexModeMetaPanel<ODatabase<?>, DisplayMode, String, V> implements IDisplayModeAware{
    public static final List<String> ODATABASE_ATTRS = new ArrayList<String>();
    public static final List<String> TIMEZONES;
    static
    {
        for(ATTRIBUTES attr: ATTRIBUTES.values()) {
            ODATABASE_ATTRS.add(attr.name());
        }
        
        String[] timezones = TimeZone.getAvailableIDs();
        Arrays.sort(timezones);
        TIMEZONES = Arrays.asList(timezones);
    }

    private static final long serialVersionUID = 1L;
    private static final List<String> CLUSTER_SELECTIONS =
            Arrays.asList(ODefaultClusterSelectionStrategy.NAME, ORoundRobinClusterSelectionStrategy.NAME, OBalancedClusterSelectionStrategy.NAME);

    public ODatabaseMetaPanel(String id, IModel<DisplayMode> modeModel, IModel<ODatabase<?>> entityModel,
                              IModel<String> criteryModel) {
        super(id, modeModel, entityModel, criteryModel);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected V getValue(ODatabase<?> entity, String critery) {
    	if(ATTRIBUTES.VALIDATION.name().equals(critery)) {
    		Boolean value = (Boolean) entity.get(ATTRIBUTES.VALIDATION);
    		if(value==null) value = ((ODatabaseDocument)entity).isValidationEnabled();
    		return (V) (value!=null?value:Boolean.TRUE);
    	} else if (ATTRIBUTES.CUSTOM.name().equals(critery)){
    		List<OStorageEntryConfiguration> properties = (List<OStorageEntryConfiguration>) entity.get(ATTRIBUTES.CUSTOM);
    		StringBuilder sb = new StringBuilder();
    		if(properties!=null) {
	    		for (OStorageEntryConfiguration entry : properties) {
					sb.append(entry.name).append("=").append(entry.value).append('\n');
				}
    		}
    		return (V)sb.toString();
    	}
    	else {
    		return (V) entity.get(ATTRIBUTES.valueOf(critery));
    	}
    }

    @Override
    protected void setValue(ODatabase<?> entity, String critery, V value) {
        ODatabaseDocument db = OrientDbWebSession.get().getDatabase();
        db.commit();
        try
        {
            if(ATTRIBUTES.CLUSTERSELECTION.name().equals(critery))
            {
                if(value!=null) entity.set(ATTRIBUTES.valueOf(critery), value.toString());
            } else if(ATTRIBUTES.CUSTOM.name().equals(critery)) { 
            	if(value!=null) {
            		String stringValue = value.toString();
            		String[] customs = stringValue.split("\\r?\\n");
            		for (String custom : customs) {
						if(custom.indexOf('=')>0) entity.set(ATTRIBUTES.CUSTOM, custom);
					}
            	} else {
            		entity.set(ATTRIBUTES.CUSTOM, "clear");
            	}
            }else {
                entity.set(ATTRIBUTES.valueOf(critery), value);
            }
        } finally
        {
            db.begin();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Component resolveComponent(String id, DisplayMode mode, String critery) {
        if(DisplayMode.EDIT.equals(mode) && !OSecurityHelper.isAllowed(ORule.ResourceGeneric.SCHEMA, null, OrientPermission.UPDATE))
        {
            mode = DisplayMode.VIEW;
        }
        if(DisplayMode.VIEW.equals(mode))
        {
        	if(ATTRIBUTES.VALIDATION.name().equals(critery)) {
        		return new BooleanViewPanel(id, (IModel<Boolean>)getModel()).setDefaultValue(true);
        	} else if(ATTRIBUTES.CUSTOM.name().equals(critery)) {
        		return new MultiLineLabel(id, getModel());
        	}
        	else {
        		return new Label(id, getModel());
        	}
        }
        else if(DisplayMode.EDIT.equals(mode)) {
            if(ATTRIBUTES.CLUSTERSELECTION.name().equals(critery))
            {
                return new DropDownChoice<String>(id, (IModel<String>)getModel(), CLUSTER_SELECTIONS);
            } else if(ATTRIBUTES.STATUS.name().equals(critery))
            {
                return new DropDownChoice<STATUS>(id, (IModel<STATUS>)getModel(), Arrays.asList(STATUS.values()));
            } else if(ATTRIBUTES.VALIDATION.name().equals(critery)) {
            	return new BooleanEditPanel(id, (IModel<Boolean>)getModel());
            } else if(ATTRIBUTES.CUSTOM.name().equals(critery)) {
            	return new TextArea<String>(id, (IModel<String>) getModel());
            } else if (ATTRIBUTES.TIMEZONE.name().equals(critery)) {
            	return new DropDownChoice<String>(id, (IModel<String>)getModel(), TIMEZONES);
            }
            else if(ATTRIBUTES.TYPE.name().equals(critery)){
                return resolveComponent(id, DisplayMode.VIEW, critery);
            } else if(ATTRIBUTES.DATEFORMAT.name().equals(critery) || ATTRIBUTES.DATETIMEFORMAT.name().equals(critery)) {
            	return new TextField<String>(id, (IModel<String>)getModel()).setType(String.class)
            								.add(DateFormatValidator.SIMPLE_DATE_FORMAT_VALIDATOR);
            }
            else {
                return new TextField<V>(id, getModel()).setType(String.class);
            }
        }
            return null;
    }

    @Override
    protected IModel<String> newLabelModel() {
        return new SimpleNamingModel<String>("database."+getPropertyObject().toLowerCase());
    }
}
