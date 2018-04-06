package ch.ntb.jass.common.proto.player_messages;

import ch.ntb.jass.common.entities.PlayerEntity;
import ch.ntb.jass.common.proto.ToServerMessage;

/**
 * This Message is sent to the Server when the player wishes to join the Lobby (This is the when the player wants to connect to the server)
 * @param playerData Contains some Data about the player. playerData.id and playerData.seatNr have no purpose here.
 */
public class JoinLobbyMessage extends ToServerMessage {
	public PlayerEntity playerData;
}
