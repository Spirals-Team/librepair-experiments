
package mage.cards.v;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author emerald000
 */
public final class VedalkenOrrery extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public VedalkenOrrery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // You may cast spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)));
    }

    public VedalkenOrrery(final VedalkenOrrery card) {
        super(card);
    }

    @Override
    public VedalkenOrrery copy() {
        return new VedalkenOrrery(this);
    }
}
