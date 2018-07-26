

package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author Viserion, North
 */
public final class PistonSledge extends CardImpl {

    private static FilterControlledPermanent filter = new FilterControlledPermanent("an artifact");

    static  {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public PistonSledge (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(Outcome.BoostCreature, "attach it to target creature you control"), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 1)));
        this.addAbility(new EquipAbility(Outcome.AddAbility, new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    public PistonSledge (final PistonSledge card) {
        super(card);
    }

    @Override
    public PistonSledge copy() {
        return new PistonSledge(this);
    }
}
