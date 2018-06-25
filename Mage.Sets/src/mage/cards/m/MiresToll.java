
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class MiresToll extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("the number of Swamps you control");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }
    
    public MiresToll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target player reveals a number of cards from their hand equal to the number of Swamps you control. You choose one of them. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(TargetController.ANY, new PermanentsOnBattlefieldCount(filter)));

    }

    public MiresToll(final MiresToll card) {
        super(card);
    }

    @Override
    public MiresToll copy() {
        return new MiresToll(this);
    }
}
