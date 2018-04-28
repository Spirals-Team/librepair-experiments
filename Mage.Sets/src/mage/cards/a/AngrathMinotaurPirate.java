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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public class AngrathMinotaurPirate extends CardImpl {

    public AngrathMinotaurPirate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGRATH);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +2: Angrath, Minotaur Pirate deals 1 damage to target opponent and each creature that player controls.
        Effects effects1 = new Effects();
        effects1.add(new DamageTargetEffect(1));
        effects1.add(new DamageAllControlledTargetEffect(1, new FilterCreaturePermanent())
                .setText("and each creature that player or that planeswalker’s controller controls")
        );
        LoyaltyAbility ability1 = new LoyaltyAbility(effects1, +2);
        ability1.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability1);

        // -3: Return target Pirate card from your graveyard to the battlefield.
        FilterCard filterPirateCard = new FilterCreatureCard("pirate card from your graveyard");
        filterPirateCard.add(new SubtypePredicate(SubType.PIRATE));
        Ability ability2 = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("Return target Pirate card from your graveyard to the battlefield"), -3);
        ability2.addTarget(new TargetCardInYourGraveyard(filterPirateCard));
        this.addAbility(ability2);

        // -11: Destroy all creature target opponent controls.  Angrath, Minotaur Pirate deals damage to that player equal to their total power.
        Ability ability3 = new LoyaltyAbility(new AngrathMinotaurPirateThirdAbilityEffect(), -11);
        ability3.addTarget(new TargetOpponent());
        this.addAbility(ability3);
    }

    public AngrathMinotaurPirate(final AngrathMinotaurPirate card) {
        super(card);
    }

    @Override
    public AngrathMinotaurPirate copy() {
        return new AngrathMinotaurPirate(this);
    }
}

class AngrathMinotaurPirateThirdAbilityEffect extends OneShotEffect {

    public AngrathMinotaurPirateThirdAbilityEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creature target opponent controls.  {this} deals damage to that player equal to their total power";
    }

    public AngrathMinotaurPirateThirdAbilityEffect(final AngrathMinotaurPirateThirdAbilityEffect effect) {
        super(effect);
    }

    @Override
    public AngrathMinotaurPirateThirdAbilityEffect copy() {
        return new AngrathMinotaurPirateThirdAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetOpponent != null) {
            int powerSum = 0;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getSourceId(), game)) {
                permanent.destroy(source.getSourceId(), game, false);
                powerSum += permanent.getPower().getValue();
            }
            game.applyEffects();
            targetOpponent.damage(powerSum, source.getSourceId(), game, false, true);
        }
        return true;
    }
}
