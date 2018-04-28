/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com AS IS AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.StaticAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author spjspj
 */
public class TetraviteToken extends TokenImpl {

    public TetraviteToken() {
        super("Tetravite", "1/1 colorless Tetravite artifact creature token");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.TETRAVITE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new CantBeEnchantedAbility());
    }

    public TetraviteToken(final TetraviteToken token) {
        super(token);
    }

    public TetraviteToken copy() {
        return new TetraviteToken(this);
    }
}

class CantBeEnchantedAbility extends StaticAbility {

    public CantBeEnchantedAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public CantBeEnchantedAbility(final CantBeEnchantedAbility ability) {
        super(ability);
    }

    @Override
    public CantBeEnchantedAbility copy() {
        return new CantBeEnchantedAbility(this);
    }

    public boolean canTarget(MageObject source, Game game) {
        return !(source.isEnchantment()
                && source.hasSubtype(SubType.AURA, game));
    }

}
