
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class GloriousCharge extends CardImpl {

    public GloriousCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false));
    }

    public GloriousCharge(final GloriousCharge card) {
        super(card);
    }

    @Override
    public GloriousCharge copy() {
        return new GloriousCharge(this);
    }
}
