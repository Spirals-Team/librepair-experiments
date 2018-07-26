
package mage.cards.p;

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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherCardPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public final class PestilenceRats extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other Rats on the battlefield");
    static{
        filter.add(new SubtypePredicate(SubType.RAT));
        filter.add(new AnotherCardPredicate());
    }

    public PestilenceRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Pestilence Rats's power is equal to the number of other Rats on the battlefield. (For example, as long as there are two other Rats on the battlefield, Pestilence Rats's power and toughness are 2/3.)
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
    }

    public PestilenceRats(final PestilenceRats card) {
        super(card);
    }

    @Override
    public PestilenceRats copy() {
        return new PestilenceRats(this);
    }
}
