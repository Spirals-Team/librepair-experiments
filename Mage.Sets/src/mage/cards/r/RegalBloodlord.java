package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.game.permanent.token.BatToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author TheElk801
 */
public final class RegalBloodlord extends CardImpl {

    public RegalBloodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, if you gained life this turn, create a 1/1 black Bat creature token with flying.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new CreateTokenEffect(new BatToken()),
                        TargetController.ANY, false
                ),
                new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0),
                "At the beginning of each end step, "
                + "if you gained life this turn, "
                + "create a 1/1 black Bat creature token with flying."
        ), new PlayerGainedLifeWatcher());
    }

    public RegalBloodlord(final RegalBloodlord card) {
        super(card);
    }

    @Override
    public RegalBloodlord copy() {
        return new RegalBloodlord(this);
    }
}
