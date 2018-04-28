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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author North
 */
public class MorticianBeetle extends CardImpl {

    public MorticianBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player sacrifices a creature, you may put a +1/+1 counter on Mortician Beetle.
        this.addAbility(new PlayerSacrificesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true));
    }

    public MorticianBeetle(final MorticianBeetle card) {
        super(card);
    }

    @Override
    public MorticianBeetle copy() {
        return new MorticianBeetle(this);
    }
}

class PlayerSacrificesCreatureTriggeredAbility extends TriggeredAbilityImpl {

    public PlayerSacrificesCreatureTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public PlayerSacrificesCreatureTriggeredAbility(final PlayerSacrificesCreatureTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.SACRIFICED_PERMANENT) {
            MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (mageObject != null && mageObject.isCreature()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player sacrifices a creature, " + super.getRule();
    }

    @Override
    public PlayerSacrificesCreatureTriggeredAbility copy() {
        return new PlayerSacrificesCreatureTriggeredAbility(this);
    }
}