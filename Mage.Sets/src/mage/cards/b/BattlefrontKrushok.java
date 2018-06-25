
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAllEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class BattlefrontKrushok extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with a +1/+1 counter on it");

    static {
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public BattlefrontKrushok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Battlefront Krushok can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByMoreThanOneSourceEffect()));

        // Each creature you control with a +1/+1 counter on it can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByMoreThanOneAllEffect(filter)));
    }

    public BattlefrontKrushok(final BattlefrontKrushok card) {
        super(card);
    }

    @Override
    public BattlefrontKrushok copy() {
        return new BattlefrontKrushok(this);
    }
}
