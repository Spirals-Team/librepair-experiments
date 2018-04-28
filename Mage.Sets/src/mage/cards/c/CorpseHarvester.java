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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class CorpseHarvester extends CardImpl {

    public CorpseHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{B}, {tap}, Sacrifice a creature: Search your library for a Zombie card and a Swamp card, reveal them, and put them into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CorpseHarvesterEffect(), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    public CorpseHarvester(final CorpseHarvester card) {
        super(card);
    }

    @Override
    public CorpseHarvester copy() {
        return new CorpseHarvester(this);
    }
}

class CorpseHarvesterEffect extends OneShotEffect {

    CorpseHarvesterEffect() {
        super(Outcome.DrawCard);
        staticText = "Search your library for a Zombie card and a Swamp card, reveal them, and put them into your hand. Then shuffle your library";
    }

    CorpseHarvesterEffect(final CorpseHarvesterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        searchCard(you, source, game, cards, "Zombie");
        searchCard(you, source, game, cards, "Swamp");
        you.revealCards("Corpse Harvester", cards, game);
        you.shuffleLibrary(source, game);
        return true;
    }

    private void searchCard(Player player, Ability source, Game game, Cards cards, String subtype) {
        FilterCard filter = new FilterCard(subtype);
        filter.add(new SubtypePredicate(SubType.byDescription(subtype)));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, game)) {
            Card card = player.getLibrary().remove(target.getFirstTarget(), game);
            if (card != null) {
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                cards.add(card);
            }
        }
    }

    @Override
    public CorpseHarvesterEffect copy() {
        return new CorpseHarvesterEffect(this);
    }
}
