/*
 * Copyright (C) 2014 Language In Interaction
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experiment.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.mpi.tg.eg.experiment.client.model.colour.StimulusResponseGroup;

/**
 * @since Oct 14, 2014 1:11:22 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class UserResults {

    private UserData userData = null;
    private GameData gameData = new GameData();
    private final List<StimulusResponseGroup> results = new ArrayList<>();

    public UserResults(UserData userId) {
        this.userData = userId;
    }

    public void setUser(UserData userId) {
        this.userData = userId;
        gameData = new GameData();
    }

    public UserData getUserData() {
        return userData;
    }

    public void clearResults() {
        // todo: is this needed? 
        gameData = new GameData();
    }

    public GameData getGameData() {
        return gameData;
    }

    public void addStimulusResponseGroup(StimulusResponseGroup stimulusResponseGroup) {
        results.add(stimulusResponseGroup);
    }

    public List<StimulusResponseGroup> getStimulusResponseGroups() {
        return Collections.unmodifiableList(results);
    }
}
