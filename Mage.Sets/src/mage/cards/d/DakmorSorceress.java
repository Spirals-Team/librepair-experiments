
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public final class DakmorSorceress extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Swamps you control");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public DakmorSorceress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Dakmor Sorceress's power is equal to the number of Swamps you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
    }

    public DakmorSorceress(final DakmorSorceress card) {
        super(card);
    }

    @Override
    public DakmorSorceress copy() {
        return new DakmorSorceress(this);
    }
}
