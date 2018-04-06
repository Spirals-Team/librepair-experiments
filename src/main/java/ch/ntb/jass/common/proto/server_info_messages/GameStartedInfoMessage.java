package ch.ntb.jass.common.proto.server_info_messages;

import ch.ntb.jass.common.entities.TeamEntity;
import ch.ntb.jass.common.proto.ToPlayerMessage;

/**
 * This is a informationmessage sent to all players.
 * Informationmessage to all players that the game started.
 * The teams are also defined with this message.
 * @param teams The teams which are playing against each other.
 */

public class GameStartedInfoMessage extends ToPlayerMessage{
	public TeamEntity[] teams = new TeamEntity[2];
}
