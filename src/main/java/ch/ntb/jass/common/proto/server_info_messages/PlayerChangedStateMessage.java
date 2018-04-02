package ch.ntb.jass.common.proto.server_info_messages;

import ch.ntb.jass.common.entities.PlayerEntity;
import ch.ntb.jass.common.proto.ToPlayerMessage;

/**
 * This Message is the server Response to the ChangeStateMessage a player has sent.
 * @param player The player who changed his state
 * @param isReady true if the player wants to start the game. 
 */

public class PlayerChangedStateMessage extends ToPlayerMessage {
	public PlayerEntity player;
	public boolean isReady;
}
