package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class SarkhansDragonfire extends CardImpl {

    private static final FilterCard filter = new FilterCard("a red card");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public SarkhansDragonfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Sarkhan's Dragonfire deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Look at the top five cards of your library. You may reveal a red card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                new StaticValue(5), false, new StaticValue(1), filter,
                Zone.LIBRARY, false, true, false, Zone.HAND, true, false, false
        ).setBackInRandomOrder(true).setText("Look at the top five cards of your library. "
                + "You may reveal a red card from among them and put it into your hand. "
                + "Put the rest on the bottom of your library in a random order.")
        );
    }

    public SarkhansDragonfire(final SarkhansDragonfire card) {
        super(card);
    }

    @Override
    public SarkhansDragonfire copy() {
        return new SarkhansDragonfire(this);
    }
}
