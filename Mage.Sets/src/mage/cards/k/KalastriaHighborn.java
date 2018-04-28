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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author maurer.it_at_gmail.com, TheElk801
 */
public class KalastriaHighborn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vampire you control");

    static {
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public KalastriaHighborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(new DoIfCostPaid(new LoseGainEffect(), new ManaCostsImpl("{B}")), false, filter);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public KalastriaHighborn(final KalastriaHighborn card) {
        super(card);
    }

    @Override
    public KalastriaHighborn copy() {
        return new KalastriaHighborn(this);
    }
}

class LoseGainEffect extends OneShotEffect {

    LoseGainEffect() {
        super(Outcome.Benefit);
        this.staticText = "target player loses 2 life and you gain 2 life";
    }

    LoseGainEffect(final LoseGainEffect effect) {
        super(effect);
    }

    @Override
    public LoseGainEffect copy() {
        return new LoseGainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player them = game.getPlayer(source.getFirstTarget());
        if (you == null && them == null) {
            return false;
        }
        if (you != null) {
            you.gainLife(2, game, source);
        }
        if (them != null) {
            them.loseLife(2, game, false);
        }
        return true;
    }
}
