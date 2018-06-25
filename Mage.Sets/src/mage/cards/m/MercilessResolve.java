
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class MercilessResolve extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a creature or land");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.LAND)));
    }

    public MercilessResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // As an additional cost to cast Merciless Resolve, sacrifice a creature or land.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    public MercilessResolve(final MercilessResolve card) {
        super(card);
    }

    @Override
    public MercilessResolve copy() {
        return new MercilessResolve(this);
    }
}
