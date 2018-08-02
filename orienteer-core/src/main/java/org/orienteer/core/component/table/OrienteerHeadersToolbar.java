package org.orienteer.core.component.table;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.sort.AjaxFallbackOrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.NoFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.orienteer.core.component.FAIconType;

import java.util.List;

/**
 * Toolbar for Orienteer table.
 * @param <T>
 *           the type of an table objects
 * @param <S>
 *           the type of the sorting parameter
 */
public class OrienteerHeadersToolbar<T, S> extends AbstractToolbar {

    private FilterForm<?> filterForm;
    private final ISortStateLocator<S> stateLocator;

    private final List<S> filteredColumns = Lists.newArrayList();
    private final String filteredColumnClass;

    /**
     * Constructor
     *
     * @param table
     * @param stateLocator
     */
    public OrienteerHeadersToolbar(DataTable<T, S> table, ISortStateLocator<S> stateLocator) {
        super(table);
        this.stateLocator = stateLocator;
        table.setOutputMarkupId(true);
        filteredColumnClass = "filtered-column";
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onInitialize() {
        super.onInitialize();
        ListView<IColumn<T, S>> headers = new ListView<IColumn<T,S>>("headers", (List<IColumn<T, S>>) getTable().getColumns()) {
            @Override
            protected void populateItem(final ListItem<IColumn<T, S>> item) {

                final IColumn<T, S> column = item.getModelObject();
                WebMarkupContainer container = new WebMarkupContainer("container") {
                    @Override
                    protected void onComponentTag(ComponentTag tag) {
                        super.onComponentTag(tag);
                        changeColorForFilteredColumn(tag, item.getModelObject());
                    }
                };
                WebMarkupContainer header;

                if (column.isSortable()) {
                    header = newSortableHeader("header", column.getSortProperty(), stateLocator);
                    header.add(newIconContainer("sortIcon", column.getSortProperty(), stateLocator));
                } else {
                    header = new WebMarkupContainer("header");
                    header.add(new WebMarkupContainer("sortIcon"));
                }
                if (column instanceof IFilteredColumn && filterForm != null) {
                    IFilteredColumn<T, S> filteredColumn = (IFilteredColumn<T, S>) column;
                    Component filter = filteredColumn.getFilter("filter", filterForm);
                    container.add(filter != null ? filter : new NoFilter("filter"));
                } else container.add(new NoFilter("filter"));

                if (column instanceof AbstractColumn) {
                    AbstractColumn<?, ?> abstractColumn = (AbstractColumn<?, ?>) column;

                    if (abstractColumn.getHeaderColspan() > 1) {
                        header.add(AttributeModifier.replace("colspan", abstractColumn.getHeaderColspan()));
                    }

                    if (abstractColumn.getHeaderRowspan() > 1) {
                        header.add(AttributeModifier.replace("rowspan", abstractColumn.getHeaderRowspan()));
                    }
                }
                header.add(column.getHeader("label"));
                container.add(header);
                item.add(container);
                item.setRenderBodyOnly(true);
            }
        };
        headers.setReuseItems(true);
        add(headers);
    }


    /**
     * Factory method for sortable header components. A sortable header component must have id of
     * <code>headerId</code> and conform to markup specified in <code>OrienteerHeadersToolbar.html</code>
     *
     * @param headerId
     *            header component id
     * @param property
     *            property this header represents
     * @param locator
     *            sort state locator
     * @return created header component
     */
    protected WebMarkupContainer newSortableHeader(final String headerId, final S property,
                                                   final ISortStateLocator<S> locator)
    {
        WebMarkupContainer container = new AjaxFallbackOrderByBorder<S>(headerId, property, locator) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
            }

            @Override
            protected void onAjaxClick(final AjaxRequestTarget target) {
                target.add(getTable());
            }

            @Override
            protected void onSortChanged() {
                super.onSortChanged();
                getTable().setCurrentPage(0);
            }

            @Override
            public void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                tag.remove("class");
            }
        };
        return container;
    }


    private WebMarkupContainer newIconContainer(String id, final S property, final ISortStateLocator<S> locator) {
        return new WebMarkupContainer(id) {
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                ISortState<S> sortState = locator.getSortState();
                SortOrder sortOrder = sortState.getPropertySortOrder(property);
                String iconClass;
                if (sortOrder == SortOrder.ASCENDING) {
                    iconClass = FAIconType.sort_asc.getCssClass();
                } else if (sortOrder == SortOrder.DESCENDING) {
                    iconClass = FAIconType.sort_desc.getCssClass();
                } else {
                    iconClass = FAIconType.sort.getCssClass();
                }
                if (!Strings.isNullOrEmpty(iconClass)) {
                    tag.append("class", iconClass, " ");
                }
            }
        };
    }

    /**
     * Change color for filtered column
     * @param tag html tag of current column
     * @param column {@link IColumn} column for change color
     */
    public void changeColorForFilteredColumn(ComponentTag tag, IColumn<T, S> column) {
        if (filteredColumnClass == null)
            return;
        String aClass = tag.getAttribute("class");
        if (needChangeColor(column)) {
            aClass = aClass != null ? aClass + " " + filteredColumnClass : filteredColumnClass;
        } else {
            if (aClass != null && aClass.contains(filteredColumnClass)) {
                aClass = aClass.substring(0, aClass.indexOf(filteredColumnClass));
            } else if (aClass == null) aClass = "";
        }
        if (!Strings.isNullOrEmpty(aClass)) {
            tag.put("class", aClass);
        } else tag.put("class", "");
    }

    private boolean needChangeColor(IColumn<T, S> column) {
        boolean filter = false;
        if (column instanceof OPropertyValueColumn) {
            OPropertyValueColumn valueColumn = (OPropertyValueColumn) column;
            OProperty property = valueColumn.getCriteryModel().getObject();
            if (property != null) {
                filter = filteredColumns.contains(property.getName());
            }
        } else filteredColumns.contains(column.getSortProperty());
        return filter;
    }

    public OrienteerHeadersToolbar<T, S> setFilterForm(FilterForm<?> filterForm) {
        this.filterForm = filterForm;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected DataTable<T, S> getTable() {
        return (DataTable<T, S>) super.getTable();
    }

    public void addFilteredColumn(S sort) {
        filteredColumns.add(sort);
    }

    public void clearFilteredColumns() {
        filteredColumns.clear();
    }
}
