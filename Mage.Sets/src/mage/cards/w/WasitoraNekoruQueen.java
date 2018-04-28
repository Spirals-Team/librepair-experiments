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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WasitoraCatDragonToken;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public class WasitoraNekoruQueen extends CardImpl {

    public WasitoraNekoruQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Wasitora, Nekoru Queen deals combat damage to a player, that player sacrifices a creature. If the player can't, you create a 3/3 black, red, and green Cat Dragon creature token with flying
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new WasitoraNekoruQueenEffect(), false, true));
    }

    public WasitoraNekoruQueen(final WasitoraNekoruQueen card) {
        super(card);
    }

    @Override
    public WasitoraNekoruQueen copy() {
        return new WasitoraNekoruQueen(this);
    }
}

class WasitoraNekoruQueenEffect extends OneShotEffect {

    public WasitoraNekoruQueenEffect() {
        super(Outcome.Benefit);
        staticText = "that player sacrifices a creature. If the player can't, you create a 3/3 black, red, and green Cat Dragon creature token with flying";
    }

    public WasitoraNekoruQueenEffect(final WasitoraNekoruQueenEffect effect) {
        super(effect);
    }

    @Override
    public WasitoraNekoruQueenEffect copy() {
        return new WasitoraNekoruQueenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (damagedPlayer != null && controller != null) {
            FilterControlledPermanent filter = new FilterControlledPermanent("creature");
            filter.add(new CardTypePredicate(CardType.CREATURE));
            TargetPermanent target = new TargetPermanent(1, 1, filter, true);
            if (damagedPlayer.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
                Permanent objectToBeSacrificed = game.getPermanent(target.getFirstTarget());
                if (objectToBeSacrificed != null) {
                    if (objectToBeSacrificed.sacrifice(source.getSourceId(), game)) {
                        return true;
                    }
                }
            }
            new CreateTokenEffect(new WasitoraCatDragonToken()).apply(game, source);
            return true;
        }
        return false;
    }
}
