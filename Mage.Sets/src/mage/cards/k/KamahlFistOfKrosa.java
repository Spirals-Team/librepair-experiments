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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Backfir3
 */
public class KamahlFistOfKrosa extends CardImpl {

    public KamahlFistOfKrosa(UUID ownerId, CardSetInfo setInfo) {
        
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {G}: Target land becomes a 1/1 creature until end of turn. It's still a land.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesCreatureTargetEffect(new KamahlFistOfKrosaLandToken(), false, true, Duration.EndOfTurn),
                new ManaCostsImpl("{G}"));
		ability.addTarget(new TargetLandPermanent());
		this.addAbility(ability);
		
        // {2}{G}{G}{G}: Creatures you control get +3/+3 and gain trample until end of turn.
        SimpleActivatedAbility boostAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(3, 3, Duration.EndOfTurn),
                new ManaCostsImpl("{2}{G}{G}{G}"));
		boostAbility.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));
		this.addAbility(boostAbility);
    }

    public KamahlFistOfKrosa(final KamahlFistOfKrosa card) {
        super(card);
    }

    @Override
    public KamahlFistOfKrosa copy() {
        return new KamahlFistOfKrosa(this);
    }
}

class KamahlFistOfKrosaLandToken extends TokenImpl {

    public KamahlFistOfKrosaLandToken() {
        super("", "1/1 creature");
        this.cardType.add(CardType.CREATURE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }
    public KamahlFistOfKrosaLandToken(final KamahlFistOfKrosaLandToken token) {
        super(token);
    }

    public KamahlFistOfKrosaLandToken copy() {
        return new KamahlFistOfKrosaLandToken(this);
    }
}
