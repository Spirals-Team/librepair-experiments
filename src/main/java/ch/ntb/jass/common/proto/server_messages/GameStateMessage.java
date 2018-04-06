package ch.ntb.jass.common.proto.server_messages;

import ch.ntb.jass.common.entities.*;

import java.util.Map;

/**
 * Note that this message extends another message.
 * This is a Message sent to the player after he joins the server.
 * It will send the complete state of the game to the player.
 * Note that depending on the state of the game most of the following fields can be null.
 * 
 * @param teams The teams which are competing.
 * @param currentPlayer The player whos turn it is.
 * @param hands Information about the cards each player has. (Usually color and value are null/UNKNOWN as only the number of cards is important) (The key of the Dictionary is the seatNr of each player)
 * @param deck The current state of the cards which are laid on the table.
 * @param trump Current trump.
 * @param score Current score.
 * 
 */

public class GameStateMessage extends LobbyStateMessage {
    public TeamEntity[] teams;
    public PlayerEntity currentPlayer;
    public Map<Integer, CardEntity[]> hands;
    public CardEntity[] deck;
    public TrumpEntity trump;
    public ScoreEntity score;
}
