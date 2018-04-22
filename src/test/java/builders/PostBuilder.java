package builders;

import model.Coord;
import model.Post;
import model.User;
import model.Vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostBuilder {

    private Vehicle vehicle = VehicleBuilder.aVehicle().build();
    private User ownerUser = UserBuilder.anUser().build();
    private Coord pickUpCoord = CoordBuilder.anCoord().build();
    private List<Coord> returnCoords = new ArrayList<Coord>();    
    private LocalDateTime sinceDate= LocalDateTime.now();
    private LocalDateTime untilDate=(LocalDateTime.now().plusDays(3L));
    private double costPerHour;

    public static PostBuilder aPost(){
        PostBuilder builder= new PostBuilder();
        builder.returnCoords.add(builder.pickUpCoord);
        return builder;
    }

    public Post build(){
        return new Post(this.vehicle, this.ownerUser, this.pickUpCoord,
                this.returnCoords, this.sinceDate, this.untilDate, this.costPerHour);
    }

    public PostBuilder withUntilDate(LocalDateTime UntilDate){
        this.untilDate = UntilDate;
        return this;
    }

    public PostBuilder withSinceDate(LocalDateTime sinceDate){
        this.sinceDate = sinceDate;
        return this;
    }

    public PostBuilder withUserDisabled(){
        this.ownerUser = UserBuilder.anUser().buildUserDisabled();
        return this;
    }

    public PostBuilder whitCostPerHour(double cost){
        this.costPerHour = cost;
        return this;
    }
    
    public PostBuilder withPickUpCoord(Coord pickUpCoord){
    	this.pickUpCoord=pickUpCoord;
    	return this;
    }
    
    public PostBuilder withReturnCoords(List<Coord> returnCoords){
    	this.returnCoords=returnCoords;
    	return this;
    }
}
