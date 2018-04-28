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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class Temper extends CardImpl {

    public Temper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{1}{W}");

        // Prevent the next X damage that would be dealt to target creature this turn. For each 1 damage prevented this way, put a +1/+1 counter on that creature.
        this.getSpellAbility().addEffect(new TemperPreventDamageTargetEffect(new ManacostVariableValue(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public Temper(final Temper card) {
        super(card);
    }

    @Override
    public Temper copy() {
        return new Temper(this);
    }
}

class TemperPreventDamageTargetEffect extends PreventionEffectImpl {

    private int amount;
    private final DynamicValue dVal;
    private boolean initialized;

    public TemperPreventDamageTargetEffect(DynamicValue dVal, Duration duration) {
        super(duration);
        this.initialized = false;
        this.dVal = dVal;
        staticText = "Prevent the next X damage that would be dealt to target creature this turn. For each 1 damage prevented this way, put a +1/+1 counter on that creature";
    }

    public TemperPreventDamageTargetEffect(final TemperPreventDamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.dVal = effect.dVal;
        this.initialized = effect.initialized;
    }

    @Override
    public TemperPreventDamageTargetEffect copy() {
        return new TemperPreventDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (!initialized) {
            amount = dVal.calculate(game, source, this);
            initialized = true;
        }
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int prevented = 0;
            if (event.getAmount() >= this.amount) {
                int damage = amount;
                event.setAmount(event.getAmount() - amount);
                this.used = true;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
                prevented = damage;
            } else {
                int damage = event.getAmount();
                event.setAmount(0);
                amount -= damage;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
                prevented = damage;
            }

            // add counters now
            if (prevented > 0) {
                Permanent targetPermanent = game.getPermanent(source.getTargets().getFirstTarget());
                if (targetPermanent != null) {
                    targetPermanent.addCounters(CounterType.P1P1.createInstance(prevented), source, game);
                    game.informPlayers(new StringBuilder("Temper: Prevented ").append(prevented).append(" damage ").toString());
                    game.informPlayers("Temper: Adding " + prevented + " +1/+1 counters to " + targetPermanent.getName());
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            if (source.getTargets().getFirstTarget().equals(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }

}
