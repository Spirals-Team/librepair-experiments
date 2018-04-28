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
package mage.cards.n;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public class NoMercy extends CardImpl {

    public NoMercy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Whenever a creature deals damage to you, destroy it.
        this.addAbility(new NoMercyTriggeredAbility());
    }

    public NoMercy(final NoMercy card) {
        super(card);
    }

    @Override
    public NoMercy copy() {
        return new NoMercy(this);
    }

    public static class NoMercyTriggeredAbility extends TriggeredAbilityImpl {

        public NoMercyTriggeredAbility() {
            super(Zone.BATTLEFIELD, new DestroyTargetEffect());
        }

        public NoMercyTriggeredAbility(final NoMercyTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public NoMercyTriggeredAbility copy() {
            return new NoMercyTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == EventType.DAMAGED_PLAYER;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getPlayerId().equals(this.getControllerId())) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent != null && permanent.isCreature()) {
                    this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a creature deals damage to you, destroy it";
        }

    }
}
