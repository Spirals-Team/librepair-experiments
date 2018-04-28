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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.watchers.common.CastFromHandWatcher;

/**
 * @author LevelX
 */
public class MyojinOfSeeingWinds extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public MyojinOfSeeingWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{U}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.getSpellAbility().addWatcher(new CastFromHandWatcher());

        // Myojin of Seeing Winds enters the battlefield with a divinity counter on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.DIVINITY.createInstance()), CastFromHandSourceCondition.instance, ""), "{this} enters the battlefield with a divinity counter on it if you cast it from your hand"));
        // Myojin of Seeing Winds is indestructible as long as it has a divinity counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                new SourceHasCounterCondition(CounterType.DIVINITY), "{this} is indestructible as long as it has a divinity counter on it")));
        // Remove a divinity counter from Myojin of Seeing Winds: Draw a card for each permanent you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)), new RemoveCountersSourceCost(CounterType.DIVINITY.createInstance()));
        this.addAbility(ability);
    }

    public MyojinOfSeeingWinds(final MyojinOfSeeingWinds card) {
        super(card);
    }

    @Override
    public MyojinOfSeeingWinds copy() {
        return new MyojinOfSeeingWinds(this);
    }
}
