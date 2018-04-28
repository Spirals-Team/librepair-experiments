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
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class IzzetKeyrune extends CardImpl {

    public IzzetKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {U} or {R}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // {U}{R}: Until end of turn, Izzet Keyrune becomes a 2/1 blue and red Elemental artifact creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new IzzetKeyruneToken(), "", Duration.EndOfTurn), new ManaCostsImpl("{U}{R}")));

        // Whenever Izzet Keyrune deals combat damage to a player, you may draw a card. If you do, discard a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new IzzetKeyruneEffect(), true));
    }

    public IzzetKeyrune(final IzzetKeyrune card) {
        super(card);
    }

    @Override
    public IzzetKeyrune copy() {
        return new IzzetKeyrune(this);
    }

    private static class IzzetKeyruneEffect extends OneShotEffect {

        public IzzetKeyruneEffect() {
            super(Outcome.DrawCard);
            this.staticText = "you may draw a card. If you do, discard a card";
        }

        public IzzetKeyruneEffect(final IzzetKeyruneEffect effect) {
            super(effect);
        }

        @Override
        public IzzetKeyruneEffect copy() {
            return new IzzetKeyruneEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null && player.chooseUse(Outcome.DrawCard, "Do you wish to draw a card? If you do, discard a card.", source, game)) {
                if (player.drawCards(1, game) > 0) {
                    player.discard(1, source, game);
                }
                return true;
            }
            return false;
        }
    }

    private static class IzzetKeyruneToken extends TokenImpl {
        IzzetKeyruneToken() {
            super("", "2/1 blue and red Elemental artifact creature");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setBlue(true);
            color.setRed(true);
            this.subtype.add(SubType.ELEMENTAL);
            power = new MageInt(2);
            toughness = new MageInt(1);
        }

        public IzzetKeyruneToken(final IzzetKeyruneToken token) {
            super(token);
        }

        public IzzetKeyruneToken copy() {
            return new IzzetKeyruneToken(this);
        }
    }
}

