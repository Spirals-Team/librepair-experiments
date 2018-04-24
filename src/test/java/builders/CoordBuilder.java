package builders;

import model.Coord;

public class CoordBuilder {
	
	private double lat= 0.0;
	private double lng= 0.0;
	
	public static CoordBuilder anCoord(){
		return new CoordBuilder();
	}
	
	public Coord build(){
		return new Coord(lat,lng);
	}
	
	public CoordBuilder withLat(double lat){
		this.lat=lat;
		return this;
	}
	
	public CoordBuilder withLng(double lng){
		this.lng=lng;
		return this;
	}

}
