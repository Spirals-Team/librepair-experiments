
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 *
 * @author Loki
 */
public final class WrathOfGod extends CardImpl {

    public WrathOfGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Destroy all creatures. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyAllEffect(FILTER_PERMANENT_CREATURES, true));
    }

    public WrathOfGod(final WrathOfGod card) {
        super(card);
    }

    @Override
    public WrathOfGod copy() {
        return new WrathOfGod(this);
    }
}
