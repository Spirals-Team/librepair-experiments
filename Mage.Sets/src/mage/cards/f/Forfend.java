
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 *
 * @author emerald000
 */
public final class Forfend extends CardImpl {

    public Forfend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Prevent all damage that would be dealt to creatures this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageToAllEffect(Duration.EndOfTurn, FILTER_PERMANENT_CREATURES));
    }

    public Forfend(final Forfend card) {
        super(card);
    }

    @Override
    public Forfend copy() {
        return new Forfend(this);
    }
}
