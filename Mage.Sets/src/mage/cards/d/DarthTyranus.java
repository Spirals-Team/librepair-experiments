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
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class DarthTyranus extends CardImpl {

    public DarthTyranus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{1}{W}{U}{B}");
        this.subtype.add(SubType.DOOKU);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Up to one target creature gets -6/-0 until your next turn.
        Effect effect = new BoostTargetEffect(-6, 0, Duration.UntilYourNextTurn);
        effect.setText("Up to one target creature gets -6/-0 until your next turn");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -3: Sacrifice an artifact. If you do, you may search your library for an artifact card and put that card onto the battlefield. Shuffle your library.
        this.addAbility(new LoyaltyAbility(new TransmuteArtifactEffect(), -3));

        // -6: Target player's life total becomes 5. Another target players's life total becomes 30.
        ability = new LoyaltyAbility(new DarthTyranusEffect(), -6);
        ability.addTarget(new TargetPlayer(2));
        this.addAbility(ability);
    }

    public DarthTyranus(final DarthTyranus card) {
        super(card);
    }

    @Override
    public DarthTyranus copy() {
        return new DarthTyranus(this);
    }
}

class DarthTyranusEffect extends OneShotEffect {

    public DarthTyranusEffect() {
        super(Outcome.Benefit);
        staticText = "Target player's life total becomes 5. Another target players's life total becomes 30";
    }

    public DarthTyranusEffect(DarthTyranusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player1 = game.getPlayer(targetPointer.getTargets(game, source).get(0));
        Player player2 = game.getPlayer(targetPointer.getTargets(game, source).get(1));
        if (player1 != null && player2 != null) {
            player1.setLife(5, game, source);
            player1.setLife(30, game, source);
            return true;
        }
        return false;
    }

    @Override
    public DarthTyranusEffect copy() {
        return new DarthTyranusEffect(this);
    }
}

class TransmuteArtifactEffect extends SearchEffect {

    public TransmuteArtifactEffect() {
        super(new TargetCardInLibrary(new FilterArtifactCard()), Outcome.PutCardInPlay);
        staticText = "Sacrifice an artifact. If you do, search your library for an artifact card and put that card onto the battlefield. Shuffle your library";
    }

    public TransmuteArtifactEffect(final TransmuteArtifactEffect effect) {
        super(effect);
    }

    @Override
    public TransmuteArtifactEffect copy() {
        return new TransmuteArtifactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean sacrifice = false;
            TargetControlledPermanent targetArtifact = new TargetControlledPermanent(new FilterControlledArtifactPermanent());
            if (controller.chooseTarget(Outcome.Sacrifice, targetArtifact, source, game)) {
                Permanent permanent = game.getPermanent(targetArtifact.getFirstTarget());
                if (permanent != null) {
                    sacrifice = permanent.sacrifice(source.getSourceId(), game);
                }
            }
            if (sacrifice && controller.searchLibrary(target, game)) {
                if (!target.getTargets().isEmpty()) {
                    for (UUID cardId : target.getTargets()) {
                        Card card = controller.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                            controller.shuffleLibrary(source, game);
                            return true;
                        }
                    }
                }
                controller.shuffleLibrary(source, game);
            }
        }
        return false;
    }
}
