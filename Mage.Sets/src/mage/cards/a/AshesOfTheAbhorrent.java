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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author TheElk801
 */
public class AshesOfTheAbhorrent extends CardImpl {

    public AshesOfTheAbhorrent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Players can't cast spells from graveyards or activate abilities of cards in graveyards.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AshesOfTheAbhorrentEffect()));

        // Whenever a creature dies, you gain 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new GainLifeEffect(1), false));
    }

    public AshesOfTheAbhorrent(final AshesOfTheAbhorrent card) {
        super(card);
    }

    @Override
    public AshesOfTheAbhorrent copy() {
        return new AshesOfTheAbhorrent(this);
    }
}

class AshesOfTheAbhorrentEffect extends ContinuousRuleModifyingEffectImpl {

    public AshesOfTheAbhorrentEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Players can't cast spells from graveyards or activate abilities of cards in graveyards";
    }

    public AshesOfTheAbhorrentEffect(final AshesOfTheAbhorrentEffect effect) {
        super(effect);
    }

    @Override
    public AshesOfTheAbhorrentEffect copy() {
        return new AshesOfTheAbhorrentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL
                || event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null) {
            Zone zone = game.getState().getZone(card.getId());
            if (zone != null && (zone == Zone.GRAVEYARD)) {
                return true;
            }
        }
        return false;
    }
}
