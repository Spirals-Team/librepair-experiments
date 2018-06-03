
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North, Loki
 */
public final class AshmouthHound extends CardImpl {

    public AshmouthHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.HOUND);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Ashmouth Hound blocks or becomes blocked by a creature, Ashmouth Hound deals 1 damage to that creature.
        this.addAbility(new BlocksOrBecomesBlockedTriggeredAbility(new DamageTargetEffect(1, true, "that creature"), false));
    }

    public AshmouthHound(final AshmouthHound card) {
        super(card);
    }

    @Override
    public AshmouthHound copy() {
        return new AshmouthHound(this);
    }
}
