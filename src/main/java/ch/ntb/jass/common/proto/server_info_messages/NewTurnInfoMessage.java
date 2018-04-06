package ch.ntb.jass.common.proto.server_info_messages;

import ch.ntb.jass.common.entities.*;
import ch.ntb.jass.common.proto.ToPlayerMessage;

/**
 * This is a message from the server sent to all players
 * Message that defines who's playing next
 * All players are required to check if they are the next player.
 * @param nextPlayer. The player who has to choose a card
 * @param selectWeis. If true the player can select his wiis in this turn
 */

public class NewTurnInfoMessage extends ToPlayerMessage {
    public PlayerEntity nextPlayer;
    public boolean selectWeis;
}
