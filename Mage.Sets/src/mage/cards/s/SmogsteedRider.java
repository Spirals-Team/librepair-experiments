
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author Loki
 */
public final class SmogsteedRider extends CardImpl {

    public SmogsteedRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityControlledEffect(FearAbility.getInstance(), Duration.EndOfTurn, new FilterAttackingCreature(), true), false));
    }

    public SmogsteedRider(final SmogsteedRider card) {
        super(card);
    }

    @Override
    public SmogsteedRider copy() {
        return new SmogsteedRider(this);
    }
}
