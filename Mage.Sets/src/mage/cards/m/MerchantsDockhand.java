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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class MerchantsDockhand extends CardImpl {

    public MerchantsDockhand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {3}{U}, {T}, Tap X untapped artifacts you control: Look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MerchantsDockhandEffect(), new ManaCostsImpl<>("{3}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapXTargetCost());
        this.addAbility(ability);
    }

    public MerchantsDockhand(final MerchantsDockhand card) {
        super(card);
    }

    @Override
    public MerchantsDockhand copy() {
        return new MerchantsDockhand(this);
    }
}

class MerchantsDockhandEffect extends OneShotEffect {

    public MerchantsDockhandEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order";
    }

    public MerchantsDockhandEffect(final MerchantsDockhandEffect effect) {
        super(effect);
    }

    @Override
    public MerchantsDockhandEffect copy() {
        return new MerchantsDockhandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }

        int xValue = source.getManaCostsToPay().getX();

        for (Cost cost : source.getCosts()) {
            if (cost instanceof TapXTargetCost) {
                xValue = ((TapXTargetCost) cost).getAmount();
                break;
            }
        }

        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, xValue));
        controller.lookAtCards(sourceObject.getIdName(), cards, game);

        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into your hand"));
        target.setNotTarget(true);
        if (controller.chooseTarget(Outcome.DrawCard, cards, target, source, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
                cards.remove(card);
            }
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}

class TapXTargetCost extends VariableCostImpl {

    final static FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("untapped artifacts you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public TapXTargetCost() {
        super("controlled untapped artifacts you would like to tap");
        this.text = "Tap X untapped artifacts you control";
    }

    public TapXTargetCost(final TapXTargetCost cost) {
        super(cost);
    }

    @Override
    public TapXTargetCost copy() {
        return new TapXTargetCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetControlledPermanent target = new TargetControlledPermanent(xValue, xValue, filter, true);
        return new TapTargetCost(target);
    }

}
