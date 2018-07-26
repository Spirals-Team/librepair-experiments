
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class HeraldOfAnguish extends CardImpl {

    public HeraldOfAnguish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, each opponent discards a card.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT), false));

        // {1}{B}, Sacrifice an artifact: Target creature gets -2/-2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent())));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public HeraldOfAnguish(final HeraldOfAnguish card) {
        super(card);
    }

    @Override
    public HeraldOfAnguish copy() {
        return new HeraldOfAnguish(this);
    }
}
