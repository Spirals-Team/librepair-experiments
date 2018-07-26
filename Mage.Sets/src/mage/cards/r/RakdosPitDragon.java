

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class RakdosPitDragon extends CardImpl {

    public RakdosPitDragon (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        // {R}{R}: Rakdos Pit Dragon gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl("{R}{R}")));
        // {R}: Rakdos Pit Dragon gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                new ManaCostsImpl("{R}")));
        // Hellbent — Rakdos Pit Dragon has double strike as long as you have no cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                HellbentCondition.instance,
                "Hellbent - Rakdos Pit Dragon has double strike as long as you have no cards in hand")));
    }

    public RakdosPitDragon (final RakdosPitDragon card) {
        super(card);
    }

    @Override
    public RakdosPitDragon copy() {
        return new RakdosPitDragon(this);
    }

}
