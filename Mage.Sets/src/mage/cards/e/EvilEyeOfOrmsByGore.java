
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Quercitron
 */
public final class EvilEyeOfOrmsByGore extends CardImpl {

    private static final FilterCreaturePermanent cantAttackFilter = new FilterCreaturePermanent("Non-Eye creatures you control");
    private static final FilterCreaturePermanent cantBeBlockedByFilter = new FilterCreaturePermanent("except by Walls");

    static {
        cantBeBlockedByFilter.add(Predicates.not(new SubtypePredicate(SubType.WALL)));
        cantAttackFilter.add(Predicates.not((new SubtypePredicate(SubType.EYE))));
        cantAttackFilter.add(new ControllerPredicate(TargetController.YOU));
    }

    public EvilEyeOfOrmsByGore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.EYE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Non-Eye creatures you control can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAnyPlayerAllEffect(Duration.WhileOnBattlefield, cantAttackFilter)));

        // Evil Eye of Orms-by-Gore can't be blocked except by Walls.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(cantBeBlockedByFilter, Duration.WhileOnBattlefield)));
    }

    public EvilEyeOfOrmsByGore(final EvilEyeOfOrmsByGore card) {
        super(card);
    }

    @Override
    public EvilEyeOfOrmsByGore copy() {
        return new EvilEyeOfOrmsByGore(this);
    }
}
