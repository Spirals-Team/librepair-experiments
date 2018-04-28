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
package mage.cards.p;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class PureIntentions extends CardImpl {

    public PureIntentions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");
        this.subtype.add(SubType.ARCANE);

        // Whenever a spell or ability an opponent controls causes you to discard cards this turn, return those cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new PureIntentionsAllTriggeredAbility()));

        // When a spell or ability an opponent controls causes you to discard Pure Intentions, return Pure Intentions from your graveyard to your hand at the beginning of the next end step.
        this.addAbility(new PureIntentionsTriggeredAbility());
    }

    public PureIntentions(final PureIntentions card) {
        super(card);
    }

    @Override
    public PureIntentions copy() {
        return new PureIntentions(this);
    }
}

class PureIntentionsAllTriggeredAbility extends DelayedTriggeredAbility {

    public PureIntentionsAllTriggeredAbility() {
        super(new ReturnFromGraveyardToHandTargetEffect(), Duration.EndOfTurn, false);
    }

    public PureIntentionsAllTriggeredAbility(PureIntentionsAllTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject != null
                && game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId())) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getOwnerId().equals(getControllerId())) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public PureIntentionsAllTriggeredAbility copy() {
        return new PureIntentionsAllTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a spell or ability an opponent controls causes you to discard cards this turn, return those cards from your graveyard to your hand.";
    }
}

class PureIntentionsTriggeredAbility extends TriggeredAbilityImpl {

    public PureIntentionsTriggeredAbility() {
        super(Zone.ALL, new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnSourceFromGraveyardToHandEffect())), false);
    }

    public PureIntentionsTriggeredAbility(final PureIntentionsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PureIntentionsTriggeredAbility copy() {
        return new PureIntentionsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getSourceId().equals(event.getTargetId())) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject != null
                    && game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId())) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a spell or ability an opponent controls causes you to discard {this}, return {this} from your graveyard to your hand at the beginning of the next end step.";
    }
}
