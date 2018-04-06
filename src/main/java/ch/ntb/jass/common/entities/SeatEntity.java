package ch.ntb.jass.common.entities;

/**
 * Defines one of the four seats at the table.
 * Use this class to define the location of a player at the table.
 * @param seatNr The number of the seat defined by this class.
 */
public class SeatEntity {
	//seatNr is 1,2,3 or 4. It can be 0 if the player is currently not at the table.
	public int seatNr;
}
