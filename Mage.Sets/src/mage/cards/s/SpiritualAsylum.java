
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author TheElk801
 */
public final class SpiritualAsylum extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Creatures and lands you control");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)
        ));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public SpiritualAsylum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Creatures and lands you control have shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(ShroudAbility.getInstance(),
                Duration.WhileOnBattlefield, filter)));

        // When a creature you control attacks, sacrifice Spiritual Asylum.
        AttacksCreatureYouControlTriggeredAbility ability = new AttacksCreatureYouControlTriggeredAbility(new SacrificeSourceEffect());
        ability.setOnce(true);
        this.addAbility(ability);
    }

    public SpiritualAsylum(final SpiritualAsylum card) {
        super(card);
    }

    @Override
    public SpiritualAsylum copy() {
        return new SpiritualAsylum(this);
    }
}
