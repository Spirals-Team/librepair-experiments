package com.domain;

import com.Exception.EmptyRoundListException;
import com.Exception.LastRoundReachedException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
public class Rounds extends ArrayList<Round> {
    @Getter private int maxSize;

    /**
     * Unckecked exception is thrown because we don't want to add more than maxSize elements and throw a checked exception is not possible when overriding add method
     *
     * @param round Round to be added to the list
     * @return true when round is added
     */
    public boolean add(Round round) {
        if (this.size() == maxSize)
            throw new LastRoundReachedException("Last round already reached!");
        else
            return super.add(round);
    }

    Round getCurrentRound() throws EmptyRoundListException {
        if (this.size() == 0)
            throw new EmptyRoundListException("Can't get current round because no round exist yet!");
        else
            return this.get(this.size() - 1);
    }

    void createNextRound(PawnsSelection userPawns, int correctlyPlacedPawns, int misplacedPawns) {
        this.add(new Round(userPawns, correctlyPlacedPawns, misplacedPawns));
    }
}
