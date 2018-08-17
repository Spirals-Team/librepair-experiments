/*
 * Copyright (C) 2015 Language In Interaction
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

import java.util.HashMap;
import java.util.Set;
import nl.mpi.tg.eg.experiment.client.exception.MetadataFieldException;
import nl.mpi.tg.eg.experiment.client.service.MetadataFieldProvider;

/**
 * @since Mar 10, 2015 11:11:43 AM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class UserData {

    private final HashMap<MetadataField, String> metadataValues = new HashMap<>();
    private final HashMap<MetadataField, UserId> metadataConnections = new HashMap<>();
    private final UserId userId;
    private int gamesPlayed = 0;
    private int totalScore = 0;
    private int totalPotentialScore = 0;
    private int currentScore = 0;
    private int correctStreak = 0;
    private int errorStreak = 0;
    private int potentialScore = 0;
    private double maxScore = 0;
    private int maxErrors = 0;
    private int maxCorrectStreak = 0;
    private int maxErrorStreak = 0;
    private int maxPotentialScore = 0;
    private Boolean currentIsCorrect = null; // todo: this may be problematic for withStimuli

    public UserData() {
        this.userId = new UserId();
    }

    public UserData(UserId userId) {
        this.userId = userId;
    }

//    public UserData(String userLabel) {
//        this.userId = new UserId();
//        metadataValues.put(new MetadataFieldProvider().firstNameMetadataField, userLabel);
//    }
    public UserId getUserId() {
        return userId;
    }

//    public void clearMetadata() {
//        metadataValues.clear();
//    }
    public void setMetadataValue(MetadataField metadataField, String value) {
        metadataValues.put(metadataField, value);
    }

    public void setMetadataConnection(MetadataField metadataField, UserId value) {
        if (value != null && !value.toString().isEmpty()) {
            metadataConnections.put(metadataField, value);
        }
    }

    public String getMetadataValue(MetadataField metadataField) {
        final String returnString = metadataValues.get(metadataField);
        return (returnString == null) ? "" : returnString;
    }

    public UserId getMetadataConnection(MetadataField metadataField) {
        final UserId returnString = metadataConnections.get(metadataField);
        return (returnString == null) ? null : returnString;
    }

    public Set<MetadataField> getMetadataFields() {
        return metadataValues.keySet();
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void addGamePlayed() {
        this.gamesPlayed++;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setPotentialScore(int potentialScore) {
        this.potentialScore = potentialScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getCorrectStreak() {
        return correctStreak;
    }

    public void setCorrectStreak(int correctStreak) {
        this.correctStreak = correctStreak;
    }

    public int getErrorStreak() {
        return errorStreak;
    }

    public void setErrorStreak(int errorStreak) {
        this.errorStreak = errorStreak;
    }

    public void clearCurrentScore() {
        this.currentScore = 0;
        this.potentialScore = 0;
        this.correctStreak = 0;
        this.errorStreak = 0;
    }

    public void clearCurrentResponse() {
        this.currentIsCorrect = null;
    }

    public int getTotalPotentialScore() {
        return totalPotentialScore;
    }

    public void setTotalPotentialScore(int totalPotentialScore) {
        this.totalPotentialScore = totalPotentialScore;
    }

    public int getPotentialScore() {
        return potentialScore;
    }

    public void addPotentialScore(boolean currentIsCorrect) {
        this.potentialScore++;
        this.totalPotentialScore++;
        this.currentIsCorrect = currentIsCorrect;
        if (currentIsCorrect) {
            this.currentScore++;
            this.totalScore++;
            this.correctStreak++;
            this.errorStreak = 0;
        } else {
            this.errorStreak++;
            this.correctStreak = 0;
        }
        updateMaxScore(this.currentScore, this.potentialScore - this.currentScore, this.potentialScore, this.correctStreak, this.errorStreak);
    }

    public boolean isCurrentCorrect() {
        return (currentIsCorrect != null) ? currentIsCorrect : false;
    }

    public boolean isCurrentIncorrect() {
        return (currentIsCorrect != null) ? currentIsCorrect == false : false;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public int getMaxCorrectStreak() {
        return maxCorrectStreak;
    }

    public void setMaxCorrectStreak(int maxCorrectStreak) {
        this.maxCorrectStreak = maxCorrectStreak;
    }

    public int getMaxErrors() {
        return maxErrors;
    }

    public void setMaxErrors(int maxErrors) {
        this.maxErrors = maxErrors;
    }

    public int getMaxErrorStreak() {
        return maxErrorStreak;
    }

    public void setMaxErrorStreak(int maxErrorStreak) {
        this.maxErrorStreak = maxErrorStreak;
    }

    public int getMaxPotentialScore() {
        return maxPotentialScore;
    }

    public void setMaxPotentialScore(int maxPotentialScore) {
        this.maxPotentialScore = maxPotentialScore;
    }

    public void updateMaxScore(double currentScore, int currentErrors, int potentialScore, int correctStreak, int errorStreak) {
        setMaxScore((getMaxScore() < currentScore) ? currentScore : getMaxScore());
        setMaxErrors((getMaxErrors() < currentErrors) ? currentErrors : getMaxErrors());
        setMaxPotentialScore((getMaxPotentialScore() < potentialScore) ? potentialScore : getMaxPotentialScore());
        setMaxCorrectStreak((getMaxCorrectStreak() < correctStreak) ? correctStreak : getMaxCorrectStreak());
        setMaxErrorStreak((getMaxErrorStreak() < errorStreak) ? errorStreak : getMaxErrorStreak());
    }

//    public void validateNameField() throws MetadataFieldException {
//        final MetadataField firstNameMetadataField = new MetadataFieldProvider().workerIdMetadataField;
//        final String nameValue = metadataValues.get(firstNameMetadataField);
//        firstNameMetadataField.validateValue(nameValue);
//    }
}
