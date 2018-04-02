package ch.ntb.jass.common.proto.server_info_messages;

import ch.ntb.jass.common.entities.*;
import ch.ntb.jass.common.proto.ToPlayerMessage;

/**
 * This is a informationmessage sent to all players.
 * It contains the wiis one person made. 
 * This Message can be sent several times. Sometimes wiis only contain a WeisType, without 
 * an origin card. (Origin card is null in this case)
 * @param wiis All wiis a player has made (Except for STOECK as this is broadcasted separately)
 * @param player The player who made the wiis.
 */

public class WiisInfoMessage extends ToPlayerMessage {
    public WeisEntity[] wiis;
    public PlayerEntity player;
}
