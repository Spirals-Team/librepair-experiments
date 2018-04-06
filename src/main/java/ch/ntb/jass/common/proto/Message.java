package ch.ntb.jass.common.proto;

import ch.ntb.jass.common.proto.player_messages.*;
import ch.ntb.jass.common.proto.server_info_messages.*;
import ch.ntb.jass.common.proto.server_messages.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

/**
 * Message that will be sent from Server to Client.
 *
 * @param type defines de message type that will be sent.
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JoinLobbyMessage.class, name = "joinLobby"),
        @JsonSubTypes.Type(value = LeaveLobbyMessage.class, name = "leaveLobby"),
        @JsonSubTypes.Type(value = LobbyStateMessage.class, name = "lobbyState"),
        @JsonSubTypes.Type(value = PlayerLeftLobbyInfoMessage.class, name = "playerLeftLobbyInfo"),
        @JsonSubTypes.Type(value = PlayerMovedToLobbyInfoMessage.class, name = "playerMovedToLobbyInfo"),

        @JsonSubTypes.Type(value = JoinTableMessage.class, name = "joinTable"),
        @JsonSubTypes.Type(value = LeaveTableMessage.class, name = "leaveTable"),
        @JsonSubTypes.Type(value = PlayerMovedToTableInfoMessage.class, name = "playerMovedToTableInfo"),

        @JsonSubTypes.Type(value = ChooseTrumpMessage.class, name = "chooseTrump"),
        @JsonSubTypes.Type(value = ChosenTrumpMessage.class, name = "chosenTrump"),
        @JsonSubTypes.Type(value = ChosenTrumpInfoMessage.class, name = "chosenTrumpInfo"),

        @JsonSubTypes.Type(value = GameStateMessage.class, name = "gameState"),
        @JsonSubTypes.Type(value = ChosenWiisMessage.class, name = "chosenWiis"),
        @JsonSubTypes.Type(value = ChangeStateMessage.class, name = "changeState"),
        @JsonSubTypes.Type(value = HandOutCardsMessage.class, name = "handoutCards"),
        @JsonSubTypes.Type(value = PlaceCardMessage.class, name = "placeCard"),
        @JsonSubTypes.Type(value = WrongCardMessage.class, name = "wrongCard"),
        @JsonSubTypes.Type(value = PlayerChangedStateMessage.class, name = "playerChangedState"),
        @JsonSubTypes.Type(value = WiisInfoMessage.class, name = "wiisInfo"),
        @JsonSubTypes.Type(value = GameStartedInfoMessage.class, name = "gameStartedInfo"),
        @JsonSubTypes.Type(value = NewRoundInfoMessage.class, name = "newRoundInfo"),
        @JsonSubTypes.Type(value = NewTurnInfoMessage.class, name = "newTurnInfo"),
        @JsonSubTypes.Type(value = TurnInfoMessage.class, name = "turnInfo"),
        @JsonSubTypes.Type(value = EndOfRoundInfoMessage.class, name = "endOfRoundInfo"),
})
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public abstract class Message {
    /**
     * An id defined by the sender, which may be used in related responses
     */
    public String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
