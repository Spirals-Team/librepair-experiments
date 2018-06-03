
package mage.cards.b;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 * @author nantuko, Loki
 */
public final class BoldDefense extends CardImpl {

    public BoldDefense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Kicker {3}{W} (You may pay an additional {3}{W} as you cast this spell.)
        this.addAbility(new KickerAbility("{3}{W}"));

        // Creatures you control get +1/+1 until end of turn. If Bold Defense was kicked, instead creatures you control get +2/+2 and gain first strike until end of turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new BoostControlledEffect(2, 2, Duration.EndOfTurn),
                new BoostTargetEffect(1, 1, Duration.EndOfTurn), new LockedInCondition(KickedCondition.instance),
                "Creatures you control get +1/+1 until end of turn. if this spell was kicked, instead creatures you control get +2/+2"));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false),
                null, new LockedInCondition(KickedCondition.instance),
                "and gain first strike until end of turn"));
    }

    public BoldDefense(final BoldDefense card) {
        super(card);
    }

    @Override
    public BoldDefense copy() {
        return new BoldDefense(this);
    }
}
