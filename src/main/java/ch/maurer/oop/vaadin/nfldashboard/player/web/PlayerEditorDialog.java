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

import ch.maurer.oop.vaadin.nfldashboard.common.web.AbstractEditorDialog;
import ch.maurer.oop.vaadin.nfldashboard.league.business.LeagueService;
import ch.maurer.oop.vaadin.nfldashboard.player.business.PlayerService;
import ch.maurer.oop.vaadin.nfldashboard.player.db.Player;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.StringLengthValidator;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PlayerEditorDialog extends AbstractEditorDialog<Player> {

    private final TextField playerNameField = new TextField("Name");

    public PlayerEditorDialog(BiConsumer<Player, Operation> itemSaver, Consumer<Player> itemDeleter) {
        super("player", itemSaver, itemDeleter);

        addNameField();
    }

    private void addNameField() {
        getFormLayout().add(playerNameField);

        getBinder().forField(playerNameField)
                .withConverter(String::trim, String::trim)
                .withValidator(new StringLengthValidator(
                        "Player name must contain at least 3 printable characters",
                        3, null))
                .withValidator(
                        name -> PlayerService.getInstance()
                                .findPlayers(name).size() == 0,
                        "Player name must be unique")
                .bind(Player::getName, Player::setName);
    }

    @Override
    protected void confirmDelete() {
        int leagueCount = LeagueService.getInstance().findLeague(getCurrentItem().getName()).size();
        if (leagueCount > 0) {
            openConfirmationDialog("Delete player",
                    "Are you sure you want to delete the “" + getCurrentItem().getName()
                            + "” player? There are " + leagueCount
                            + " leagues associated with this player.",
                    "Deleting the player will mark the associated leagues as “undefined”. "
                            + "You can edit individual leagues to select another player.");
        }
    }
}
