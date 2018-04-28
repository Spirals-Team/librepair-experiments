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
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author TheElk801
 */
public class UrborgStalker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.ACTIVE));
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public UrborgStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of each player's upkeep, if that player controls a nonblack, nonland permanent, Urborg Stalker deals 1 damage to that player.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(1), TargetController.ANY, false),
                new PermanentsOnTheBattlefieldCondition(filter),
                "At the beginning of each player's upkeep, "
                + "if that player controls a nonblack, nonland permanent, "
                + "{this} deals 1 damage to that player."
        ));
    }

    public UrborgStalker(final UrborgStalker card) {
        super(card);
    }

    @Override
    public UrborgStalker copy() {
        return new UrborgStalker(this);
    }
}
