package ch.ntb.jass.common.proto.server_info_messages;

import ch.ntb.jass.common.entities.ScoreEntity;

/** Note: This Message extends another Message
 * This is a informationmessage sent to all players.
 * It contains the the information of the round being over with the ongoing score.
 * @param score contains the score of the ongoing game 
 * @param gameOver If the score has reached the limit, gameOver is true and the game is finished.
 */

public class EndOfRoundInfoMessage extends TurnInfoMessage {
	public ScoreEntity score;
	public boolean gameOver;
}
