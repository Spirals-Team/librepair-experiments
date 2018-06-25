
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LoneFox
 */
public final class WildernessElemental extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("nonbasic lands your opponents control");

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public WildernessElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Wilderness Elemental's power is equal to the number of nonbasic lands your opponents control.
         this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
   }

    public WildernessElemental(final WildernessElemental card) {
        super(card);
    }

    @Override
    public WildernessElemental copy() {
        return new WildernessElemental(this);
    }
}
