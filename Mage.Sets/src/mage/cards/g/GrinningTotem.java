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
package mage.cards.g;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.turn.Step;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class GrinningTotem extends CardImpl {

    public GrinningTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, {tap}, Sacrifice Grinning Totem: Search target opponent's library for a card and exile it. Then that player shuffles their library.
        // Until the beginning of your next upkeep, you may play that card.
        // At the beginning of your next upkeep, if you haven't played it, put it into its owner's graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrinningTotemSearchAndExileEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public GrinningTotem(final GrinningTotem card) {
        super(card);
    }

    @Override
    public GrinningTotem copy() {
        return new GrinningTotem(this);
    }
}

class GrinningTotemSearchAndExileEffect extends OneShotEffect {

    public GrinningTotemSearchAndExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search target opponent's library for a card and exile it. Then that player shuffles their library. " +
                "Until the beginning of your next upkeep, you may play that card. " +
                "At the beginning of your next upkeep, if you haven't played it, put it into its owner's graveyard";
    }
    
    public GrinningTotemSearchAndExileEffect(final GrinningTotemSearchAndExileEffect effect) {
        super(effect);
    }

    @Override
    public GrinningTotemSearchAndExileEffect copy() {
        return new GrinningTotemSearchAndExileEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (you != null && targetOpponent != null) {
            if (targetOpponent.getLibrary().hasCards()) {
                TargetCardInLibrary targetCard = new TargetCardInLibrary();
                if (you.searchLibrary(targetCard, game, targetOpponent.getId())) {
                    Card card = targetOpponent.getLibrary().remove(targetCard.getFirstTarget(), game);
                    if (card != null) {
                        UUID exileZoneId = CardUtil.getCardExileZoneId(game, source);
                        you.moveCardToExileWithInfo(card, exileZoneId, sourceObject != null ? sourceObject.getIdName() : "", source.getSourceId(), game, Zone.LIBRARY, true);
                        ContinuousEffect effect = new GrinningTotemMayPlayEffect();
                        effect.setTargetPointer(new FixedTarget(card.getId()));
                        game.addEffect(effect, source);

                        game.addDelayedTriggeredAbility(new GrinningTotemDelayedTriggeredAbility(exileZoneId), source);
                    }
                }
            }
            targetOpponent.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
    
}

class GrinningTotemMayPlayEffect extends AsThoughEffectImpl {
    
    private boolean sameStep = true;

    public GrinningTotemMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the beginning of your next upkeep, you may play that card.";
    }
    
    public GrinningTotemMayPlayEffect(final GrinningTotemMayPlayEffect effect) {
        super(effect);
    }

    @Override
    public GrinningTotemMayPlayEffect copy() {
        return new GrinningTotemMayPlayEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UPKEEP) {
            if (!sameStep && game.getActivePlayerId().equals(source.getControllerId()) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                return true;
            }
        } else {
            sameStep = false;
        }
        return false;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return source.getControllerId().equals(affectedControllerId)
                && sourceId.equals(getTargetPointer().getFirst(game, source));
    }
    
}

class GrinningTotemDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID exileZoneId;

    public GrinningTotemDelayedTriggeredAbility(UUID exileZoneId) {
        super(new GrinningTotemPutIntoGraveyardEffect(exileZoneId));
        this.exileZoneId = exileZoneId;
    }

    public GrinningTotemDelayedTriggeredAbility(final GrinningTotemDelayedTriggeredAbility ability) {
        super(ability);
        this.exileZoneId = ability.exileZoneId;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
        return exileZone != null && !exileZone.getCards(game).isEmpty();
    }

    @Override
    public GrinningTotemDelayedTriggeredAbility copy() {
        return new GrinningTotemDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getActivePlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "At the beginning of your next upkeep, if you haven't played it, " + modes.getText();
    }
}

class GrinningTotemPutIntoGraveyardEffect extends OneShotEffect {

    private final UUID exileZoneId;

    public GrinningTotemPutIntoGraveyardEffect(UUID exileZoneId) {
        super(Outcome.Detriment);
        this.exileZoneId = exileZoneId;
        this.staticText = "put it into its owner's graveyard";
    }
    
    public GrinningTotemPutIntoGraveyardEffect(final GrinningTotemPutIntoGraveyardEffect effect) {
        super(effect);
        this.exileZoneId = effect.exileZoneId;
    }

    @Override
    public GrinningTotemPutIntoGraveyardEffect copy() {
        return new GrinningTotemPutIntoGraveyardEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone zone = game.getExile().getExileZone(exileZoneId);
        if (controller != null && zone != null) {
            return controller.moveCards(zone, Zone.GRAVEYARD, source, game);
        }
        return false;
    }
    
}
