
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class ShrewdNegotiation extends CardImpl {

    private static final String rule = "Exchange control of target artifact you control and target artifact or creature you don't control";

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)));
    }

    public ShrewdNegotiation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");

        // Exchange control of target artifact you control and target artifact or creature you don't control.
        getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, rule, false, true));
        getSpellAbility().addTarget(new TargetControlledPermanent(new FilterControlledArtifactPermanent("artifact you control")));
        getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public ShrewdNegotiation(final ShrewdNegotiation card) {
        super(card);
    }

    @Override
    public ShrewdNegotiation copy() {
        return new ShrewdNegotiation(this);
    }
}
