
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 * @author Loki
 */
public final class CallToGlory extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Samurai");

    static {
        filter.add(new SubtypePredicate(SubType.SAMURAI));
    }

    public CallToGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        
        //Untap all creatures you control. Samurai creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURE));
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter, false));
    }

    public CallToGlory(final CallToGlory card) {
        super(card);
    }

    @Override
    public CallToGlory copy() {
        return new CallToGlory(this);
    }
}
