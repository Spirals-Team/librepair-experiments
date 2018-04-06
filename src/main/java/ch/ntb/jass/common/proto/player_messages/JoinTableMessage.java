package ch.ntb.jass.common.proto.player_messages;

import ch.ntb.jass.common.entities.SeatEntity;
import ch.ntb.jass.common.proto.ToServerMessage;

/**
 * This Message is sent to the Server when the player wishes to join the Table
 * @param preferedSeat. The seat the player wants to sit at.
 * will be confirmed or denied with the GameStartedMessage
 */
public class JoinTableMessage extends ToServerMessage {
	public SeatEntity preferedSeat;
}
