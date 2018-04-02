package ch.ntb.jass.common.proto.player_messages;

import ch.ntb.jass.common.entities.*;
import ch.ntb.jass.common.proto.ToServerMessage;

/**
 * This Message is sent to the Server, it contains the information of the Card played.
 * @param card gives the information of the card played
 */

public class PlaceCardMessage extends ToServerMessage {
    public CardEntity card;
}
