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

/**
 *
 * @author LevelX2
 */
public class BecomesRenownedSourceTriggeredAbility extends TriggeredAbilityImpl {

    private int renownValue;

    public BecomesRenownedSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BecomesRenownedSourceTriggeredAbility(final BecomesRenownedSourceTriggeredAbility ability) {
        super(ability);
        this.renownValue = ability.renownValue;
    }

    @Override
    public BecomesRenownedSourceTriggeredAbility copy() {
        return new BecomesRenownedSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_RENOWNED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            this.renownValue = event.getAmount();
            return true;
        }
        return false;
    }

    public int getRenownValue() {
        return renownValue;
    }

    @Override
    public String getRule() {
        return "When {this} becomes renowned, " + super.getRule();
    }
}
