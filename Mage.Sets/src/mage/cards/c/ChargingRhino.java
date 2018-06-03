
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ChargingRhino extends CardImpl {

    public ChargingRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.RHINO);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Charging Rhino can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByMoreThanOneSourceEffect()));
    }

    public ChargingRhino(final ChargingRhino card) {
        super(card);
    }

    @Override
    public ChargingRhino copy() {
        return new ChargingRhino(this);
    }
}
