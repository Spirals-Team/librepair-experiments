
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BloodcrazedHoplite extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public BloodcrazedHoplite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Heroic - Whenever you cast a spell that targets Bloodcrazed Hoplite, put a +1/+1 counter on it.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), false)));
        // Whenever a +1/+1 counter is put on Bloodcrazed Hoplite, remove a +1/+1 counter from target creature an opponent controls.
        Ability ability = new BloodcrazedHopliteTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public BloodcrazedHoplite(final BloodcrazedHoplite card) {
        super(card);
    }

    @Override
    public BloodcrazedHoplite copy() {
        return new BloodcrazedHoplite(this);
    }
}

class BloodcrazedHopliteTriggeredAbility extends TriggeredAbilityImpl {

    public BloodcrazedHopliteTriggeredAbility() {
        super(Zone.ALL, new RemoveCounterTargetEffect(CounterType.P1P1.createInstance()), false);
    }

    public BloodcrazedHopliteTriggeredAbility(BloodcrazedHopliteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId()) && event.getData().equals(CounterType.P1P1.getName());
    }

    @Override
    public BloodcrazedHopliteTriggeredAbility copy() {
        return new BloodcrazedHopliteTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a +1/+1 counter is put on {this}, " + super.getRule();
    }
}
