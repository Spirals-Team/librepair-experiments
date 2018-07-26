
package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 * @author JayDi85
 */
public final class SwaggeringCorsair extends CardImpl {

    public SwaggeringCorsair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Raid</i> &mdash; Swaggering Corsair enters the battlefield with a +1/+1 counter on it if you attacked with a creature this turn.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                RaidCondition.instance,
                "<i>Raid</i> &mdash; {this} enters the battlefield with a +1/+1 counter on it if you attacked with a creature this turn.", ""
        ), new PlayerAttackedWatcher());
    }

    public SwaggeringCorsair(final SwaggeringCorsair card) {
        super(card);
    }

    @Override
    public SwaggeringCorsair copy() {
        return new SwaggeringCorsair(this);
    }
}
