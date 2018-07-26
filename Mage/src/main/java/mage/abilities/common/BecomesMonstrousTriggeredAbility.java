/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public class BecomesMonstrousTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesMonstrousTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public BecomesMonstrousTriggeredAbility(final BecomesMonstrousTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesMonstrousTriggeredAbility copy() {
        return new BecomesMonstrousTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_MONSTROUS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature()
                && (permanent.isControlledBy(getControllerId()))) {
            this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control becomes monstrous, " + super.getRule();
    }
}
