
package mage.cards.t;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Luna Skyrise
 */
public final class TimberlineRidge extends CardImpl {

    public TimberlineRidge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Timberline Ridge doesn't untap during your untap step if it has a depletion counter on it.
        Effect effect = new ConditionalContinuousRuleModifyingEffect(new DontUntapInControllersUntapStepSourceEffect(false, true),
                new SourceHasCounterCondition(CounterType.DEPLETION, 1, Integer.MAX_VALUE));
        effect.setText("{this} doesn't untap during your untap step if it has a depletion counter on it");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);
        // At the beginning of your upkeep, remove a depletion counter from Timberline Ridge.
        Ability ability2 = new BeginningOfUpkeepTriggeredAbility(new RemoveCounterSourceEffect(CounterType.DEPLETION.createInstance()), TargetController.YOU, false);
        this.addAbility(ability2);
        // {tap}: Add {R} or {G}. Put a depletion counter on Timberline Ridge.
        Ability ability3 = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(1), new TapSourceCost());
        ability3.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance()));
        this.addAbility(ability3);
        Ability ability4 = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(1), new TapSourceCost());
        ability4.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance()));
        this.addAbility(ability4);
    }

    public TimberlineRidge(final TimberlineRidge card) {
        super(card);
    }

    @Override
    public TimberlineRidge copy() {
        return new TimberlineRidge(this);
    }
}
