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
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class DebtToTheDeathless extends CardImpl {

    public DebtToTheDeathless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}{B}{B}");

        // Each opponent loses two times X life. You gain life equal to the life lost this way.
        this.getSpellAbility().addEffect(new DebtToTheDeathlessEffect());
    }

    public DebtToTheDeathless(final DebtToTheDeathless card) {
        super(card);
    }

    @Override
    public DebtToTheDeathless copy() {
        return new DebtToTheDeathless(this);
    }
}

class DebtToTheDeathlessEffect extends OneShotEffect {

    public DebtToTheDeathlessEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent loses two times X life. You gain life equal to the life lost this way";
    }

    public DebtToTheDeathlessEffect(final DebtToTheDeathlessEffect effect) {
        super(effect);
    }

    @Override
    public DebtToTheDeathlessEffect copy() {
        return new DebtToTheDeathlessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int lifeLost = 0;
            int xValue = source.getManaCostsToPay().getX();
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    lifeLost += opponent.loseLife(xValue * 2, game, false);
                }
            }
            controller.gainLife(lifeLost, game, source);
            return true;
        }
        return false;
    }
}
