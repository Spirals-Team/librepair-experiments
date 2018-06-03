
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class VizierOfTheTrue extends CardImpl {

    public VizierOfTheTrue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // You may exert Vizier of the True as it attacks.
        this.addAbility(new ExertAbility(null, false));

        // Whenever you exert a creature, tap target creature an opponent controls.
        this.addAbility(new VizierOfTheTrueAbility());
    }

    public VizierOfTheTrue(final VizierOfTheTrue card) {
        super(card);
    }

    @Override
    public VizierOfTheTrue copy() {
        return new VizierOfTheTrue(this);
    }
}

class VizierOfTheTrueAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public VizierOfTheTrueAbility() {
        super(Zone.BATTLEFIELD, new TapTargetEffect());
        addTarget(new TargetCreaturePermanent(filter));
    }

    public VizierOfTheTrueAbility(final VizierOfTheTrueAbility ability) {
        super(ability);
    }

    @Override
    public VizierOfTheTrueAbility copy() {
        return new VizierOfTheTrueAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.BECOMES_EXERTED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever you exert a creature, tap target creature an opponent controls.";
    }
}
