package ch.ntb.jass.common.proto.player_messages;

import ch.ntb.jass.common.proto.ToServerMessage;

/**
 * This Message is sent to the Server when the player is sitting at the table waiting for the game to start
 * If he wants to Start isReady = true, if he wants to wait for something isReady = false.
 * The Server can start the game automtically when all players are ready.
 * @param isReady true if the player wants to start the game. 
 */

public class ChangeStateMessage extends ToServerMessage {
	public boolean isReady;
}
