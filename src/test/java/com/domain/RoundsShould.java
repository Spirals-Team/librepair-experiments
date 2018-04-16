package com.domain;

import com.Exception.EmptyRoundListException;
import com.Exception.LastRoundReachedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class RoundsShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throw_exception_when_list_is_empty() throws EmptyRoundListException {
        //Given
        int maxRounds = 5;
        Rounds aListOfRounds = new Rounds(maxRounds);

        //When
        thrown.expect(EmptyRoundListException.class);
        thrown.expectMessage("Can't get current round because no round exist yet!");
        aListOfRounds.getCurrentRound();

        //Then
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Test
    public void return_current_round_when_list_is_not_empty_or_full() throws EmptyRoundListException {
        //Given
        int maxRounds = 5;
        Rounds aListOfRounds = new Rounds(maxRounds);
        PawnsSelection aUserPawnsList = new PawnsSelection();
        aListOfRounds.createNextRound(aUserPawnsList, 0, 0);

        //When
        Round result = aListOfRounds.getCurrentRound();

        //Then
        assertThat(result).isNotNull();
        assertThat(aListOfRounds.indexOf(result)).isEqualTo(aListOfRounds.size() - 1);
    }

    @Test
    public void throw_exception_when_last_round_is_reached() {
        //Given
        int maxRounds = 1;
        Rounds aListOfRounds = new Rounds(maxRounds);
        PawnsSelection aUserPawnsList = new PawnsSelection();
        aListOfRounds.createNextRound(aUserPawnsList, 0, 0);

        //When
        thrown.expect(LastRoundReachedException.class);
        thrown.expectMessage("Last round already reached!");
        aListOfRounds.createNextRound(aUserPawnsList, 0, 0);

        //Then
    }
}
