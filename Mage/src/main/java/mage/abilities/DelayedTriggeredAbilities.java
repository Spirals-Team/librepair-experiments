/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.abilities;

import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Iterator;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DelayedTriggeredAbilities extends AbilitiesImpl<DelayedTriggeredAbility> {

    public DelayedTriggeredAbilities() {
    }

    public DelayedTriggeredAbilities(final DelayedTriggeredAbilities abilities) {
        super(abilities);
    }

    @Override
    public DelayedTriggeredAbilities copy() {
        return new DelayedTriggeredAbilities(this);
    }

    public void checkTriggers(GameEvent event, Game game) {
        if (this.size() > 0) {
            for (Iterator<DelayedTriggeredAbility> it = this.iterator(); it.hasNext(); ) {
                DelayedTriggeredAbility ability = it.next();
                if (ability.getDuration() == Duration.Custom) {
                    if (ability.isInactive(game)) {
                        it.remove();
                        continue;
                    }
                }
                if (!ability.checkEventType(event, game)) {
                    continue;
                }
                if (ability.checkTrigger(event, game)) {
                    ability.trigger(game, ability.controllerId);
                    if (ability.getTriggerOnlyOnce()) {
                        it.remove();
                    }
                }
            }
        }
    }

    public void removeEndOfTurnAbilities() {
        this.removeIf(ability -> ability.getDuration() == Duration.EndOfTurn);
    }

    public void removeEndOfCombatAbilities() {
        this.removeIf(ability -> ability.getDuration() == Duration.EndOfCombat);
    }


}

