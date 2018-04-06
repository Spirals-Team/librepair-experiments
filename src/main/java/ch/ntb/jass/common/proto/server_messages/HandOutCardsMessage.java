package ch.ntb.jass.common.proto.server_messages;

import ch.ntb.jass.common.entities.*;
import ch.ntb.jass.common.proto.ToPlayerMessage;

/**
 * This is a message from the server sent to a player
 * Tells the player which card he's got.
 * @param cards cards that are handed out to the player
 */

public class HandOutCardsMessage extends ToPlayerMessage {
    public CardEntity[] cards;
}
