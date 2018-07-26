
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 *
 * @author Backfir3
 */
public final class AbsoluteGrace extends CardImpl {

    public AbsoluteGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        Ability ability = ProtectionAbility.from(ObjectColor.BLACK);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield, FILTER_PERMANENT_CREATURES, false)));
    }

    public AbsoluteGrace(final AbsoluteGrace card) {
        super(card);
    }

    @Override
    public AbsoluteGrace copy() {
        return new AbsoluteGrace(this);
    }

}
