package ch.ntb.jass.common.proto.server_info_messages;

import ch.ntb.jass.common.entities.PlayerEntity;

/**
 * This is a information message sent to all players.
 * This message is sent when a the 4th card in a round is player, thus it extends TurnInfoMessage
 * @param playerWhoWonStich Player who won the "stich"
 */

public class StichInfoMessage extends TurnInfoMessage{
	PlayerEntity playerWhoWonStich;
}
