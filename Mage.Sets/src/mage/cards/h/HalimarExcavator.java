
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class HalimarExcavator extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Allies you control");

    static {
        filter.add(new SubtypePredicate(SubType.ALLY));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public HalimarExcavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        Ability ability = new AllyEntersBattlefieldTriggeredAbility(new PutLibraryIntoGraveTargetEffect(new PermanentsOnBattlefieldCount(filter)), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public HalimarExcavator(final HalimarExcavator card) {
        super(card);
    }

    @Override
    public HalimarExcavator copy() {
        return new HalimarExcavator(this);
    }
}
