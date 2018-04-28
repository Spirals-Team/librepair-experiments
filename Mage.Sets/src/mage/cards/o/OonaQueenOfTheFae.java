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
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.OonaQueenFaerieToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class OonaQueenOfTheFae extends CardImpl {

    public OonaQueenOfTheFae(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U/B}{U/B}{U/B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {X}{UB}: Choose a color. Target opponent exiles the top X cards of their library. For each card of the chosen color exiled this way, create a 1/1 blue and black Faerie Rogue creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OonaQueenOfTheFaeEffect(), new ManaCostsImpl("{X}{U/B}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public OonaQueenOfTheFae(final OonaQueenOfTheFae card) {
        super(card);
    }

    @Override
    public OonaQueenOfTheFae copy() {
        return new OonaQueenOfTheFae(this);
    }
}

class OonaQueenOfTheFaeEffect extends OneShotEffect {

    public OonaQueenOfTheFaeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose a color. Target opponent exiles the top X cards of their library. For each card of the chosen color exiled this way, create a 1/1 blue and black Faerie Rogue creature token with flying";
    }

    public OonaQueenOfTheFaeEffect(final OonaQueenOfTheFaeEffect effect) {
        super(effect);
    }

    @Override
    public OonaQueenOfTheFaeEffect copy() {
        return new OonaQueenOfTheFaeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        ChoiceColor choice = new ChoiceColor();
        if (controller == null || opponent == null || !controller.choose(outcome, choice, game)) {
            return false;
        }
        int cardsWithColor = 0;
        Cards cardsToExile = new CardsImpl();
        cardsToExile.addAll(opponent.getLibrary().getTopCards(game, source.getManaCostsToPay().getX()));

        for (Card card : cardsToExile.getCards(game)) {
            if (card != null && card.getColor(game).contains(choice.getColor())) {
                cardsWithColor++;
            }
        }
        controller.moveCards(cardsToExile, Zone.EXILED, source, game);
        if (cardsWithColor > 0) {
            new CreateTokenEffect(new OonaQueenFaerieToken(), cardsWithColor).apply(game, source);
        }
        game.informPlayers("Oona: " + cardsWithColor + " Token" + (cardsWithColor != 1 ? "s" : "") + " created");
        return true;
    }
}
