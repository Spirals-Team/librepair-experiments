
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33, LevelX2
 */
public final class CabalPatriarch extends CardImpl {

    public CabalPatriarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {2}{B}, Sacrifice a creature: Target creature gets -2/-2 until end of turn.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl("{2}{B}"));
        TargetControlledPermanent target = new TargetControlledPermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT);
        ability1.addCost(new SacrificeTargetCost(target));
        ability1.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);

        // {2}{B}, Exile a creature card from your graveyard: Target creature gets -2/-2 until end of turn.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl("{2}{B}"));
        ability2.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterCreatureCard("a creature card"))));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    public CabalPatriarch(final CabalPatriarch card) {
        super(card);
    }

    @Override
    public CabalPatriarch copy() {
        return new CabalPatriarch(this);
    }
}
