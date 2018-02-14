/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2017 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package gwt.material.design.client.ui.pager;

import com.google.gwt.dom.client.Document;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.constants.TableCssName;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.ui.MaterialListValueBox;
import gwt.material.design.client.ui.html.Span;

/**
 * Widget for building the page rows selection - where you can set the range of row count into listbox
 *
 * @author kevzlou7979
 */
public class PageRowSelection extends MaterialWidget {

    private final MaterialDataPager pager;
    protected Span rowsPerPageLabel = new Span("Rows per page");
    protected MaterialListValueBox<Integer> listPageRows = new MaterialListValueBox<>();

    public PageRowSelection(MaterialDataPager pager) {
        super(Document.get().createDivElement(), TableCssName.ROWS_PER_PAGE_PANEL);
        this.pager = pager;

        setHideOn(HideOn.HIDE_ON_SMALL_DOWN);
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        load();
    }

    protected void load() {
        add(listPageRows);
        add(rowsPerPageLabel);

        listPageRows.clear();
        for (int limitOption : pager.getLimitOptions()) {
            listPageRows.addItem(limitOption, true);
        }
        registerHandler(listPageRows.addValueChangeHandler(valueChangeEvent -> {
            pager.setLimit(valueChangeEvent.getValue());
            pager.updateRowsPerPage(valueChangeEvent.getValue());
        }));
    }
}
