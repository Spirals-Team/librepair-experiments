package ch.ntb.jass.common.entities;

public class PlayerEntity {
	public String name;
	public SeatEntity seat;
	public boolean isBot;
	public int id;
	
	//Methods
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PlayerEntity)
			return this.id == ((PlayerEntity)obj).id;
		else
			return super.equals(obj);
	}
}
