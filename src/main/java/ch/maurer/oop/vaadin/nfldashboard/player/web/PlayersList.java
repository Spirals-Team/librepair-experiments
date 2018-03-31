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
package ch.maurer.oop.vaadin.nfldashboard.player.web;

import ch.maurer.oop.vaadin.nfldashboard.common.web.MainLayout;
import ch.maurer.oop.vaadin.nfldashboard.league.business.LeagueService;
import ch.maurer.oop.vaadin.nfldashboard.league.db.League;
import ch.maurer.oop.vaadin.nfldashboard.player.business.PlayerService;
import ch.maurer.oop.vaadin.nfldashboard.player.db.Player;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "players", layout = MainLayout.class)
@PageTitle("Players List")
public class PlayersList extends VerticalLayout {

    private final TextField searchField = new TextField("", "Search players");
    private final H2 header = new H2("Players");
    private final Grid<Player> grid = new Grid<>();

    public PlayersList() {
        initView();

        addSearchBar();
        addContent();

        updateView();
    }

    private void initView() {
        addClassName("players-list");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
    }

    private void addSearchBar() {
        Div viewToolbar = new Div();
        viewToolbar.addClassName("view-toolbar");

        searchField.setPrefixComponent(new Icon("lumo", "search"));
        searchField.addClassName("view-toolbar__search-field");
        searchField.addValueChangeListener(e -> updateView());
        searchField.setValueChangeMode(ValueChangeMode.EAGER);

        viewToolbar.add(searchField);
        add(viewToolbar);
    }

    private void addContent() {
        VerticalLayout container = new VerticalLayout();
        container.setClassName("view-container");
        container.setAlignItems(Alignment.STRETCH);

        grid.addColumn(Player::getName).setHeader("Name").setWidth("8em").setResizable(true);
        grid.addColumn(this::getLeagueCount).setHeader("Beverages").setWidth("6em");
        grid.setSelectionMode(SelectionMode.NONE);

        container.add(header, grid);
        add(container);
    }

    private String getLeagueCount(Player player) {
        List<League> leaguesInPlayer = LeagueService.getInstance().findLeague(player.getName());
        int sum = leaguesInPlayer.stream().mapToInt(League::getCount).sum();
        return Integer.toString(sum);
    }

    private void updateView() {
        List<Player> players = PlayerService.getInstance().findPlayers(searchField.getValue());
        grid.setItems(players);

        if (searchField.getValue().length() > 0) {
            header.setText("Search for “" + searchField.getValue() + "”");
        } else {
            header.setText("Players");
        }
    }
}
