package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.permanent.token.AngelToken2;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author TheElk801
 */
public final class ResplendentAngel extends CardImpl {

    public ResplendentAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, if you gained 5 or more life this turn, create a 4/4 white Angel creature token with flying and vigilance.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new AngelToken2()),
                TargetController.ANY,
                new YouGainedLifeCondition(ComparisonType.MORE_THAN, 4),
                false
        ), new PlayerGainedLifeWatcher());

        // {3}{W}{W}{W}: Until end of turn, Resplendent Angel gets +2/+2 and gains lifelink.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(
                        2, 2, Duration.EndOfTurn
                ).setText("until end of turn, {this} gets +2/+2"),
                new ManaCostsImpl("{3}{W}{W}{W}")
        );
        ability.addEffect(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(),
                Duration.EndOfTurn
        ).setText("and gains lifelink"));
        this.addAbility(ability);
    }

    public ResplendentAngel(final ResplendentAngel card) {
        super(card);
    }

    @Override
    public ResplendentAngel copy() {
        return new ResplendentAngel(this);
    }
}
