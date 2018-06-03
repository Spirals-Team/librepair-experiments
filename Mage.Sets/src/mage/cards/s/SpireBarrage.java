
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class SpireBarrage extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Mountain you control");

    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public SpireBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}");

        // Spire Barrage deals damage to any target equal to the number of Mountains you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public SpireBarrage(final SpireBarrage card) {
        super(card);
    }

    @Override
    public SpireBarrage copy() {
        return new SpireBarrage(this);
    }
}
