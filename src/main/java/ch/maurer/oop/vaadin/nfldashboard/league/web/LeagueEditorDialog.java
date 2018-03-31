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

import ch.maurer.oop.vaadin.nfldashboard.common.web.AbstractEditorDialog;
import ch.maurer.oop.vaadin.nfldashboard.league.db.League;
import ch.maurer.oop.vaadin.nfldashboard.player.business.PlayerService;
import ch.maurer.oop.vaadin.nfldashboard.player.db.Player;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.DateRangeValidator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LeagueEditorDialog extends AbstractEditorDialog<League> {

    private transient PlayerService playerService = PlayerService.getInstance();

    private ComboBox<Player> playerBox = new ComboBox<>();
    private ComboBox<String> scoreBox = new ComboBox<>();
    private DatePicker lastTasted = new DatePicker();
    private TextField beverageName = new TextField();
    private TextField timesTasted = new TextField();

    public LeagueEditorDialog(BiConsumer<League, Operation> saveHandler, Consumer<League> deleteHandler) {
        super("league", saveHandler, deleteHandler);

        createNameField();
        createPlayerBox();
        createDatePicker();
        createTimesField();
        createScoreBox();
    }

    private void createScoreBox() {
        scoreBox.setLabel("Rating");
        scoreBox.setRequired(true);
        scoreBox.setAllowCustomValue(false);
        scoreBox.setItems("1", "2", "3", "4", "5");
        getFormLayout().add(scoreBox);

        getBinder().forField(scoreBox)
                .withConverter(new StringToIntegerConverter(0,
                        "The score should be a number."))
                .withValidator(new IntegerRangeValidator(
                        "The tasting count must be between 1 and 5.", 1, 5))
                .bind(League::getScore, League::setScore);
    }

    private void createDatePicker() {
        lastTasted.setLabel("Last tasted");
        lastTasted.setRequired(true);
        lastTasted.setMax(LocalDate.now());
        lastTasted.setMin(LocalDate.of(1, 1, 1));
        lastTasted.setValue(LocalDate.now());
        getFormLayout().add(lastTasted);

        getBinder().forField(lastTasted)
                .withValidator(Objects::nonNull,
                        "The date should be in MM/dd/yyyy format.")
                .withValidator(new DateRangeValidator(
                        "The date should be neither Before Christ nor in the future.",
                        LocalDate.of(1, 1, 1), LocalDate.now()))
                .bind(League::getDate, League::setDate);

    }

    private void createPlayerBox() {
        playerBox.setLabel("Player");
        playerBox.setRequired(true);
        playerBox.setItemLabelGenerator(Player::getName);
        playerBox.setAllowCustomValue(false);
        playerBox.setItems(playerService.findPlayers(""));
        getFormLayout().add(playerBox);

        getBinder().forField(playerBox)
                .withValidator(Objects::nonNull,
                        "The player should be defined.")
                .bind(League::getPlayer, League::setPlayer);
    }

    private void createTimesField() {
        timesTasted.setLabel("Times tasted");
        timesTasted.setRequired(true);
        timesTasted.setPattern("[0-9]*");
        timesTasted.setPreventInvalidInput(true);
        getFormLayout().add(timesTasted);

        getBinder().forField(timesTasted)
                .withConverter(
                        new StringToIntegerConverter(0, "Must enter a number."))
                .withValidator(new IntegerRangeValidator(
                        "The tasting count must be between 1 and 99.", 1, 99))
                .bind(League::getCount, League::setCount);
    }

    private void createNameField() {
        beverageName.setLabel("Beverage");
        beverageName.setRequired(true);
        getFormLayout().add(beverageName);

        getBinder().forField(beverageName)
                .withConverter(String::trim, String::trim)
                .withValidator(new StringLengthValidator(
                        "Beverage name must contain at least 3 printable characters",
                        3, null))
                .bind(League::getName, League::setName);
    }

    @Override
    protected void confirmDelete() {
        openConfirmationDialog("Delete league",
                "Are you sure you want to delete the league for “" + getCurrentItem().getName() + "”?", "");
    }

}
