
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactSpell;
import mage.game.permanent.token.GolemToken;

/**
 *
 * @author Loki, North
 */
public final class GolemFoundry extends CardImpl {
    public GolemFoundry (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever you cast an artifact spell, 
        FilterArtifactSpell filter = new FilterArtifactSpell("an artifact spell");
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), filter, true));
        // you may put a charge counter on Golem Foundry.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GolemToken()), new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(3))));
    }

    public GolemFoundry (final GolemFoundry card) {
        super(card);
    }

    @Override
    public GolemFoundry copy() {
        return new GolemFoundry(this);
    }
}