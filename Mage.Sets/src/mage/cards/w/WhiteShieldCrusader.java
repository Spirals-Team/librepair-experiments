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
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox

 */
public class WhiteShieldCrusader extends CardImpl {

    public WhiteShieldCrusader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // {W}: White Shield Crusader gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{W}")));

        // {W}{W}: White Shield Crusader gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,0, Duration.EndOfTurn), new ManaCostsImpl("{W}{W}")));
    }

    public WhiteShieldCrusader(final WhiteShieldCrusader card) {
        super(card);
    }

    @Override
    public WhiteShieldCrusader copy() {
        return new WhiteShieldCrusader(this);
    }
}
