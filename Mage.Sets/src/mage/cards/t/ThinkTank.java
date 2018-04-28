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
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author cbt33
 */
public class ThinkTank extends CardImpl {

    public ThinkTank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");


        // At the beginning of your upkeep, look at the top card of your library. You may put that card into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ThinkTankLookLibraryEffect(), TargetController.YOU, false));

    }

    public ThinkTank(final ThinkTank card) {
        super(card);
    }

    @Override
    public ThinkTank copy() {
        return new ThinkTank(this);
    }
}

class ThinkTankLookLibraryEffect extends OneShotEffect {

    public ThinkTankLookLibraryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top card of your library. You may put that card into your graveyard";
    }

    public ThinkTankLookLibraryEffect(final ThinkTankLookLibraryEffect effect) {
        super(effect);
    }

    @Override
    public ThinkTankLookLibraryEffect copy() {
        return new ThinkTankLookLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(card);
                    controller.lookAtCards("Think Tank", cards, game);
                    if (controller.chooseUse(Outcome.Neutral, "Do you wish to put the card into your graveyard?", source, game)) {
                        return controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    }

                }
            }
            return true;
        }
        return false;
    }
}
