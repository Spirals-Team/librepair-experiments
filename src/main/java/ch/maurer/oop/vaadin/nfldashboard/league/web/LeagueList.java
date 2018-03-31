/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ch.maurer.oop.vaadin.nfldashboard.league.web;

import ch.maurer.oop.vaadin.nfldashboard.common.business.LocalDateToStringConverter;
import ch.maurer.oop.vaadin.nfldashboard.common.business.LongToStringConverter;
import ch.maurer.oop.vaadin.nfldashboard.common.web.AbstractEditorDialog;
import ch.maurer.oop.vaadin.nfldashboard.common.web.MainLayout;
import ch.maurer.oop.vaadin.nfldashboard.league.business.LeagueService;
import ch.maurer.oop.vaadin.nfldashboard.league.db.League;
import ch.maurer.oop.vaadin.nfldashboard.league.web.LeagueList.LeagueModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.ModelItem;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.Convert;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.text.MessageFormat;
import java.util.List;

@Route(value = "", layout = MainLayout.class)
@PageTitle("League List")
@Tag("league-list")
@HtmlImport("frontend://src/views/leaguelist/league-list.html")
public class LeagueList extends PolymerTemplate<LeagueModel> {

    @Id("search")
    private TextField search;
    @Id("newLeague")
    private Button addLeague;
    @Id("header")
    private H2 header;
    private LeagueEditorDialog leagueForm = new LeagueEditorDialog(this::saveUpdate, this::deleteUpdate);

    public LeagueList() {
        search.setPlaceholder("Search leagues");
        search.addValueChangeListener(e -> updateList());
        search.setValueChangeMode(ValueChangeMode.EAGER);

        addLeague.addClickListener(e -> openForm(new League(), AbstractEditorDialog.Operation.ADD));

        updateList();

    }

    public void saveUpdate(League league, AbstractEditorDialog.Operation operation) {
        LeagueService.getInstance().saveLeague(league);
        updateList();
        Notification.show(MessageFormat.format("Beverage successfully {0}ed.", operation.getNameInText()), 3000, Position.BOTTOM_START);
    }

    public void deleteUpdate(League league) {
        LeagueService.getInstance().deleteLeague(league);
        updateList();
        Notification.show("Beverage successfully deleted.", 3000, Position.BOTTOM_START);
    }

    private void updateList() {
        List<League> leagues = LeagueService.getInstance().findLeague(search.getValue());
        if (search.isEmpty()) {
            header.setText("Leagues");
            header.add(new Span(leagues.size() + " in total"));
        } else {
            header.setText("Search for “" + search.getValue() + "”");
            if (!leagues.isEmpty()) {
                header.add(new Span(leagues.size() + " results"));
            }
        }
        getModel().setLeagues(leagues);
    }

    @EventHandler
    private void edit(@ModelItem League league) {
        openForm(league, AbstractEditorDialog.Operation.EDIT);
    }

    private void openForm(League league, AbstractEditorDialog.Operation operation) {
        if (leagueForm.getElement().getParent() == null) {
            getUI().ifPresent(ui -> ui.add(leagueForm));
        }
        leagueForm.open(league, operation);
    }

    public interface LeagueModel extends TemplateModel {
        @Convert(value = LongToStringConverter.class, path = "id")
        @Convert(value = LocalDateToStringConverter.class, path = "date")
        @Convert(value = LongToStringConverter.class, path = "player.id")
        void setLeagues(List<League> leagues);
    }

}
