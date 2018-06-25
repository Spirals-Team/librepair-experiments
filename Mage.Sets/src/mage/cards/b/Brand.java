
package mage.cards.b;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerPredicate;

/**
 *
 * @author fenhl
 */
public final class Brand extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("all permanents you own");

    static {
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public Brand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Gain control of all permanents you own. <i>(This effect lasts indefinitely.)</i>
        this.getSpellAbility().addEffect(new GainControlAllEffect(Duration.EndOfGame, filter));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new GenericManaCost(2)));
    }

    public Brand(final Brand card) {
        super(card);
    }

    @Override
    public Brand copy() {
        return new Brand(this);
    }
}
