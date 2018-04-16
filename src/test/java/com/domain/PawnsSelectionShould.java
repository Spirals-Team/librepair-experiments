package com.domain;

import com.Exception.PawnsSelectionAlreadyCompleteException;
import com.enums.Color;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PawnsSelectionShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throw_exception_when_add_and_size_is_four() {
        //Given
        PawnsSelection userPawns = new PawnsSelection();
        userPawns.add(new Pawn(Color.getRandomColor()));
        userPawns.add(new Pawn(Color.getRandomColor()));
        userPawns.add(new Pawn(Color.getRandomColor()));
        userPawns.add(new Pawn(Color.getRandomColor()));

        //When
        thrown.expect(PawnsSelectionAlreadyCompleteException.class);
        thrown.expectMessage("Pawns selection already complete!");
        userPawns.add(new Pawn(Color.getRandomColor()));

        //Then
    }
}
