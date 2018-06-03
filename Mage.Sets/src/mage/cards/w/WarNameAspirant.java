
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author emerald000
 */
public final class WarNameAspirant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 1 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 2));
    }

    public WarNameAspirant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Raid</i> &mdash; War-Name Aspirant enters the battlefield with a +1/+1 counter on it if you attacked with a creature this turn.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1), false),
                RaidCondition.instance,
                "<i>Raid</i> &mdash; {this} enters the battlefield with a +1/+1 counter on it if you attacked with a creature this turn",
                "{this} enters the battlefield with a +1/+1 counter"),
                new PlayerAttackedWatcher());

        // War-Name Aspirant can't be blocked by creatures with power 1 or less.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    public WarNameAspirant(final WarNameAspirant card) {
        super(card);
    }

    @Override
    public WarNameAspirant copy() {
        return new WarNameAspirant(this);
    }
}
