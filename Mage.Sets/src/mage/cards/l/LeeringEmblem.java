
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author Loki
 */
public final class LeeringEmblem extends CardImpl {

    public LeeringEmblem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostEquippedEffect(2, 2, Duration.EndOfTurn), false));
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public LeeringEmblem(final LeeringEmblem card) {
        super(card);
    }

    @Override
    public LeeringEmblem copy() {
        return new LeeringEmblem(this);
    }
}
