package ch.ntb.jass.common.proto.server_messages;

import ch.ntb.jass.common.entities.*;
import ch.ntb.jass.common.proto.ToPlayerMessage;

/**
 * This is a Message sent to the player after he joins the server.
 * It will send the complete state of the lobby to the player. If a game has already startet use the GameStateMessage instead.
 * @param players All players which are currently connected to the server.
 * 
 */

public class LobbyStateMessage extends ToPlayerMessage {
    public PlayerEntity[] players;
}
