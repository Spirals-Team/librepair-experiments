package ch.ntb.jass.common.proto.server_messages;

import ch.ntb.jass.common.entities.CardEntity;
import ch.ntb.jass.common.proto.ToPlayerMessage;

/**
 * This is a message from the server sent to a player
 * Tell's the player that an invalid card was played.
 * @param wrongCard invalid card before played
 */

public class WrongCardMessage extends ToPlayerMessage{
	public CardEntity wrongCard;
}
