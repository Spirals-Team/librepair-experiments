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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.PrismToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class DiamondKaleidoscope extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Prism token");

    static {
        filter.add(new TokenPredicate());
        filter.add(new SubtypePredicate(SubType.PRISM));
    }

    public DiamondKaleidoscope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {3}, {tap}: Create a 0/1 colorless Prism artifact creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new PrismToken(), 1), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Sacrifice a Prism token: Add one mana of any color.
        ability = new AnyColorManaAbility(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    public DiamondKaleidoscope(final DiamondKaleidoscope card) {
        super(card);
    }

    @Override
    public DiamondKaleidoscope copy() {
        return new DiamondKaleidoscope(this);
    }
}
