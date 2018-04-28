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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class DaruSpiritualist extends CardImpl {

    public DaruSpiritualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Cleric creature you control becomes the target of a spell or ability, it gets +0/+2 until end of turn.
        this.addAbility(new DaruSpiritualistTriggeredAbility(new BoostTargetEffect(0, 2, Duration.EndOfTurn)));
    }

    public DaruSpiritualist(final DaruSpiritualist card) {
        super(card);
    }

    @Override
    public DaruSpiritualist copy() {
        return new DaruSpiritualist(this);
    }
}

class DaruSpiritualistTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Cleric creature you control");

    static {
        filter.add(new SubtypePredicate(SubType.CLERIC));
    }

    public DaruSpiritualistTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public DaruSpiritualistTriggeredAbility(final DaruSpiritualistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DaruSpiritualistTriggeredAbility copy() {
        return new DaruSpiritualistTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        if (creature != null && filter.match(creature, getSourceId(), getControllerId(), game)) {
            this.getEffects().setTargetPointer(new FixedTarget(creature, game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Cleric creature you control becomes the target of a spell or ability, it gets +0/+2 until end of turn.";
    }
}
