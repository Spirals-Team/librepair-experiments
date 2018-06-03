
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Wehk
 */
public final class VeteranOfTheDepths extends CardImpl {

    public VeteranOfTheDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Veteran of the Depths becomes tapped, you may put a +1/+1 counter on it.
         Ability ability = new BecomesTappedSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
         this.addAbility(ability);
    }

    public VeteranOfTheDepths(final VeteranOfTheDepths card) {
        super(card);
    }

    @Override
    public VeteranOfTheDepths copy() {
        return new VeteranOfTheDepths(this);
    }
}