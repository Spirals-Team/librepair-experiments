
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.RippleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterSpell;

/**
 * @author klayhamn
 */
public final class ThrummingStone extends CardImpl {

    public ThrummingStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        addSuperType(SuperType.LEGENDARY);

        // spells you cast have Ripple 4
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(new RippleAbility(4), new FilterSpell("spells"))));
    }

    public ThrummingStone(final ThrummingStone card) {
        super(card);
    }

    @Override
    public ThrummingStone copy() {
        return new ThrummingStone(this);
    }
}
