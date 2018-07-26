
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class TimeSieve extends CardImpl {

    public TimeSieve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{U}{B}");

        // {tap}, Sacrifice five artifacts: Take an extra turn after this one.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddExtraTurnControllerEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(5, 5, new FilterControlledArtifactPermanent("five artifacts"), true)));
        this.addAbility(ability);

    }

    public TimeSieve(final TimeSieve card) {
        super(card);
    }

    @Override
    public TimeSieve copy() {
        return new TimeSieve(this);
    }
}
