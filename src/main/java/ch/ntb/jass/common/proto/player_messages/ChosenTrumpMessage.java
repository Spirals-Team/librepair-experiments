package ch.ntb.jass.common.proto.player_messages;

import ch.ntb.jass.common.entities.*;
import ch.ntb.jass.common.proto.ToServerMessage;

/**
 * This Message is sent to the Server, it contains the information of the chosen trump
 * @param trump contains the trump
 */

public class ChosenTrumpMessage extends ToServerMessage {
    public TrumpEntity trump;
}
