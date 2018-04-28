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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DiscardOntoBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LoneFox
 */
public class Dodecapod extends CardImpl {

    public Dodecapod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If a spell or ability an opponent controls causes you to discard Dodecapod, put it onto the battlefield with two +1/+1 counters on it instead of putting it into your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.HAND, new DodecapodEffect()));

    }

    public Dodecapod(final Dodecapod card) {
        super(card);
    }

    @Override
    public Dodecapod copy() {
        return new Dodecapod(this);
    }
}


class DodecapodEffect extends DiscardOntoBattlefieldEffect {

    public DodecapodEffect() {
        super();
        staticText = "If a spell or ability an opponent controls causes you to discard {this}, put it onto the battlefield with two +1/+1 counters on it instead of putting it into your graveyard";
    }

    public DodecapodEffect(final DodecapodEffect effect) {
        super(effect);
    }

    @Override
    public DodecapodEffect copy() {
        return new DodecapodEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if(super.replaceEvent(event, source, game)) {
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)).apply(game, source);
            return true;
        }
        return false;
    }
}
