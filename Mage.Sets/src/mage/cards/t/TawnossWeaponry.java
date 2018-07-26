
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class TawnossWeaponry extends CardImpl {

    public TawnossWeaponry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // You may choose not to untap Tawnos's Weaponry during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {2}, {tap}: Target creature gets +1/+1 for as long as Tawnos's Weaponry remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostTargetEffect(1, 1, Duration.Custom), SourceTappedCondition.instance,
            "target creature gets +1/+1 for as long as {this} remains tapped"), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public TawnossWeaponry(final TawnossWeaponry card) {
        super(card);
    }

    @Override
    public TawnossWeaponry copy() {
        return new TawnossWeaponry(this);
    }
}
