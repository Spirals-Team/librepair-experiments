package ch.ntb.jass.common.proto.server_info_messages;

import ch.ntb.jass.common.entities.*;
import ch.ntb.jass.common.proto.ToPlayerMessage;

/**
 * This is a information message sent to all players.
 * This message is sent when a card is played, it contains the information of the card played.
 * @param card played card
 * @param player Player who played the card
 * @param emtpyDeck This is true if the 4th card is played to the deck (Enables easy verification if the deck should be emptied).
 */

public class TurnInfoMessage extends ToPlayerMessage {
    public CardEntity laidCard;
    public PlayerEntity player;
    public boolean emptyDeck;
}
