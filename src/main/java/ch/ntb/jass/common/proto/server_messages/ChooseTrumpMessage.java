package ch.ntb.jass.common.proto.server_messages;

import ch.ntb.jass.common.proto.ToPlayerMessage;

/**
 * This is a message from the server sent to a player
 * Asks the player to choose a gamemode
 * @param mode modes that he can choose
 */

public class ChooseTrumpMessage extends ToPlayerMessage {
    public boolean canSchieben;
}
