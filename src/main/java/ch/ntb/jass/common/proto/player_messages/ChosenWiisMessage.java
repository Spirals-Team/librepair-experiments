package ch.ntb.jass.common.proto.player_messages;

import ch.ntb.jass.common.entities.*;
import ch.ntb.jass.common.proto.ToServerMessage;

/**
 * This Message is sent to the Server, it contains the information of the wiis he wants to publish
 * The server has to check if all wiis are possible and write them to the scoreboard after all wiis have
 * been published.
 * STOECK is also published with this message.
 * @param wiis All or no wiis the player wants to publish
 */

public class ChosenWiisMessage extends ToServerMessage {
    public WeisEntity[] wiis;
}
