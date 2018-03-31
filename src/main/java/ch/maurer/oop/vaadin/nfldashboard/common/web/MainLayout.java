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
package ch.maurer.oop.vaadin.nfldashboard.common.web;

import ch.maurer.oop.vaadin.nfldashboard.league.web.LeagueList;
import ch.maurer.oop.vaadin.nfldashboard.player.web.PlayersList;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Theme(Lumo.class)
public class MainLayout extends Div implements RouterLayout, AfterNavigationObserver, PageConfigurator {

    private static final String ACTIVE_ITEM_STYLE = "main-layout__nav-item--selected";

    private RouterLink league;
    private RouterLink players;
    private RouterLink team;
    private RouterLink gameCenter;

    public MainLayout() {
        H2 title = new H2("NFL-Dashboard");
        title.addClassName("main-layout__title");

        setLeagueRouterLink();
        setPlayersRouterLink();
        setTeamRouterLink();
        setGameCenterRouterLink();

        Div navigation = new Div(players, league, team, gameCenter);
        navigation.addClassName("main-layout__nav");

        Div header = new Div(title, navigation);
        header.addClassName("main-layout__header");
        add(header);

        addClassName("main-layout");
    }

    private void setLeagueRouterLink() {
        players = new RouterLink(null, LeagueList.class);
        players.add(new Icon(VaadinIcons.LIST), new Text("League"));
        players.addClassName("main-layout__nav-item");
    }

    private void setPlayersRouterLink() {
        league = new RouterLink(null, PlayersList.class);
        league.add(new Icon(VaadinIcons.USER), new Text("Players"));
        league.addClassName("main-layout__nav-item");
    }

    private void setTeamRouterLink() {
        team = new RouterLink(null, PlayersList.class);
        team.add(new Icon(VaadinIcons.USERS), new Text("Team"));
        team.addClassName("main-layout__nav-item");
    }

    private void setGameCenterRouterLink() {
        gameCenter = new RouterLink(null, PlayersList.class);
        gameCenter.add(new Icon(VaadinIcons.EXCHANGE), new Text("Game Center"));
        gameCenter.addClassName("main-layout__nav-item");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        String segment = event.getLocation().getFirstSegment();
        boolean leagueActive = segment.equals(players.getHref());
        boolean playersActive = segment.equals(league.getHref());
        boolean teamActive = segment.equals(team.getHref());
        boolean gameCenterActive = segment.equals(gameCenter.getHref());

        players.setClassName(ACTIVE_ITEM_STYLE, leagueActive);
        league.setClassName(ACTIVE_ITEM_STYLE, playersActive);
        team.setClassName(ACTIVE_ITEM_STYLE, teamActive);
        gameCenter.setClassName(ACTIVE_ITEM_STYLE, gameCenterActive);
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes");
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
    }
}
