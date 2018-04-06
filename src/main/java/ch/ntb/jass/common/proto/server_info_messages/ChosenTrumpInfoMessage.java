package ch.ntb.jass.common.proto.server_info_messages;

import ch.ntb.jass.common.entities.*;
import ch.ntb.jass.common.proto.ToPlayerMessage;

/**
 * This is a informationmessage sent to all players.
 * It contains the gamemode and the color for the mode "trumpf".
 * @param trump contains the trump that was chosen
 */

public class ChosenTrumpInfoMessage extends ToPlayerMessage {
    public TrumpEntity trump;
}
