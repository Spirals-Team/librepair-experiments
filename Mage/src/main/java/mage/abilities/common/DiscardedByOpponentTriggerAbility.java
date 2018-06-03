
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackObject;

/**
 *
 * @author Styxo
 */
public class DiscardedByOpponentTriggerAbility extends TriggeredAbilityImpl {

    public DiscardedByOpponentTriggerAbility(Effect effect) {
        this(effect, false);
    }

    public DiscardedByOpponentTriggerAbility(Effect effect, boolean optional) {
        super(Zone.GRAVEYARD, effect, optional);
    }

    public DiscardedByOpponentTriggerAbility(final DiscardedByOpponentTriggerAbility ability) {
        super(ability);
    }

    @Override
    public DiscardedByOpponentTriggerAbility copy() {
        return new DiscardedByOpponentTriggerAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getSourceId().equals(event.getTargetId())) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject != null) {
                return game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId());
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a spell or ability an opponent controls causes you to discard {this}, " + super.getRule();
    }
}
