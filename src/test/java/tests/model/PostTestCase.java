package tests.model;

import builders.PostBuilder;
import builders.UserBuilder;
import builders.VehicleBuilder;
import model.Coord;
import model.Post;
import model.User;
import model.Vehicle;
import model.exceptions.NoCoordsEnoughException;
import model.exceptions.TimeOutOfRangeException;
import model.exceptions.UserBlockedException;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class PostTestCase {

    @Test(expected=TimeOutOfRangeException.class)
    public void shouldThrowAnExceptionWhenAPostHasMoreThan5Days(){
        Post post = PostBuilder.
                aPost().
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.MAX).build();
    }

    //TODO: cambiar por un localdate que tenga media hora de diferencia.
    @Test(expected=TimeOutOfRangeException.class)
    public void shouldThrowAnExceptionWhenAPostHasLessThan1Hour(){
        Post post = PostBuilder.
                aPost().
                withSinceDate(LocalDateTime.now()).
                withUntilDate(LocalDateTime.MIN).build();
    }

    @Test(expected=UserBlockedException.class)
    public void shouldThrowAnExceptionWhenTheUserIsDisabled(){
        Post post = PostBuilder.
                aPost().
                withUserDisabled()
                .build();
    }
    
    @Test(expected=NoCoordsEnoughException.class)
    public void shouldThrowAnExceptionWhenNoSetPickUpCoord(){
    	Post post = PostBuilder
    				.aPost()
    				.withPickUpCoord(null)
    				.build();
    }
    
    @Test(expected=NoCoordsEnoughException.class)
    public void shouldThrowAnExceptionWhenNoSetReturnCoords(){
    	List<Coord> returnCoords= new ArrayList<Coord>();
    	Post post = PostBuilder
    				.aPost()
    				.withReturnCoords(returnCoords)
    				.build();
    }

    @Test
    public void shouldBeCreateAPost(){
        Vehicle vehicle= VehicleBuilder.aVehicle().build();
        User ownerUser = UserBuilder.anUser().withCredit(100).build();

        Coord coord = new Coord(1,177);
        ArrayList<Coord> listCoord = new ArrayList<Coord>();
        listCoord.add(coord);
        assertNotNull(ownerUser.post(vehicle, coord,
                listCoord, LocalDateTime.now(),
                LocalDateTime.now().plusDays(3L), 100));

    }
}
