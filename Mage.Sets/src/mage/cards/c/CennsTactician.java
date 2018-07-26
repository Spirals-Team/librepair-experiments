
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class CennsTactician extends CardImpl {
    
    private static final FilterCreaturePermanent filterSoldier = new FilterCreaturePermanent("Soldier creature");
    private static final FilterControlledCreaturePermanent filterCounter = new FilterControlledCreaturePermanent("Each creature you control with a +1/+1 counter on it");
    static {
        filterSoldier.add(new SubtypePredicate(SubType.SOLDIER));
        filterCounter.add(new CounterPredicate(CounterType.P1P1));
    }

    public CennsTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}, {tap}: Put a +1/+1 counter on target Soldier creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ColoredManaCost(ColoredManaSymbol.W));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filterSoldier));
        this.addAbility(ability);
        
        // Each creature you control with a +1/+1 counter on it can block an additional creature each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureAllEffect(1, filterCounter, Duration.WhileOnBattlefield)));
    }

    public CennsTactician(final CennsTactician card) {
        super(card);
    }

    @Override
    public CennsTactician copy() {
        return new CennsTactician(this);
    }
}
