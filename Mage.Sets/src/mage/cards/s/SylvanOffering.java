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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.ElfToken;
import mage.game.permanent.token.SylvanOfferingTreefolkToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SylvanOffering extends CardImpl {

    public SylvanOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Choose an opponent. You and that player each create an X/X green Treefolk creature token.
        this.getSpellAbility().addEffect(new SylvanOfferingEffect1());
        // Choose an opponent. You and that player each create X 1/1 green Elf Warrior creature tokens.
        this.getSpellAbility().addEffect(new SylvanOfferingEffect2());
    }

    public SylvanOffering(final SylvanOffering card) {
        super(card);
    }

    @Override
    public SylvanOffering copy() {
        return new SylvanOffering(this);
    }
}

class SylvanOfferingEffect1 extends OneShotEffect {

    SylvanOfferingEffect1() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an opponent. You and that player each create an X/X green Treefolk creature token";
    }

    SylvanOfferingEffect1(final SylvanOfferingEffect1 effect) {
        super(effect);
    }

    @Override
    public SylvanOfferingEffect1 copy() {
        return new SylvanOfferingEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                int xValue = source.getManaCostsToPay().getX();
                Effect effect = new CreateTokenTargetEffect(new SylvanOfferingTreefolkToken(xValue));
                effect.setTargetPointer(new FixedTarget(controller.getId()));
                effect.apply(game, source);
                effect.setTargetPointer(new FixedTarget(opponent.getId()));
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}

class SylvanOfferingEffect2 extends OneShotEffect {

    SylvanOfferingEffect2() {
        super(Outcome.Sacrifice);
        this.staticText = "<br>Choose an opponent. You and that player each create X 1/1 green Elf Warrior creature tokens";
    }

    SylvanOfferingEffect2(final SylvanOfferingEffect2 effect) {
        super(effect);
    }

    @Override
    public SylvanOfferingEffect2 copy() {
        return new SylvanOfferingEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                int xValue = source.getManaCostsToPay().getX();
                Effect effect = new CreateTokenTargetEffect(new ElfToken(), xValue);
                effect.setTargetPointer(new FixedTarget(controller.getId()));
                effect.apply(game, source);
                effect.setTargetPointer(new FixedTarget(opponent.getId()));
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}
