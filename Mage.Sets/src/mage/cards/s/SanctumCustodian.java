
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class SanctumCustodian extends CardImpl {

    public SanctumCustodian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Prevent the next 2 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public SanctumCustodian(final SanctumCustodian card) {
        super(card);
    }

    @Override
    public SanctumCustodian copy() {
        return new SanctumCustodian(this);
    }
}
