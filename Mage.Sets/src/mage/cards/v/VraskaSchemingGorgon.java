
package mage.cards.v;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VraskaSchemingGorgon extends CardImpl {

    public VraskaSchemingGorgon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +2: Creatures you control get +1/+0 until end of turn.
        Effect effect = new BoostControlledEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE);
        effect.setText("Creatures you control get +1/+0");
        this.addAbility(new LoyaltyAbility(effect, 2));

        // -3: Destroy target creature.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        loyaltyAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(loyaltyAbility);

        // -10: Until end of turn, creatures you control gain deathtouch and "Whenever this creature deals damage to an opponent, that player loses the game."
        loyaltyAbility = new LoyaltyAbility(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn)
                .setText("Until end of turn, creatures you control gain deathtouch"), -10);
        TriggeredAbility triggeredAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new LoseGameTargetPlayerEffect(), false, true, true);
        loyaltyAbility.addEffect(new GainAbilityControlledEffect(triggeredAbility, Duration.EndOfTurn)
                .setText("and \"Whenever this creature deals damage to an opponent, that player loses the game.\""));
        this.addAbility(loyaltyAbility);
    }

    public VraskaSchemingGorgon(final VraskaSchemingGorgon card) {
        super(card);
    }

    @Override
    public VraskaSchemingGorgon copy() {
        return new VraskaSchemingGorgon(this);
    }
}
