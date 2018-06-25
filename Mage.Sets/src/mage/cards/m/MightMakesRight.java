
package mage.cards.m;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Quercitron
 */
public final class MightMakesRight extends CardImpl {

    private static final String ruleText = "At the beginning of combat on your turn, if you control each creature on the battlefield with the greatest power, "
            + "gain control of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn.";

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public MightMakesRight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");


        // At the beginning of combat on your turn, if you control each creature on the battlefield with the greatest power, gain control
        // of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn.
        TriggeredAbility gainControlAbility = new BeginningOfCombatTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn), TargetController.YOU, false);
        gainControlAbility.addEffect(new UntapTargetEffect());
        gainControlAbility.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        gainControlAbility.addTarget(new TargetCreaturePermanent(filter));
        Ability conditionalAbility = new ConditionalTriggeredAbility(gainControlAbility, ControlsEachCreatureWithGreatestPowerCondition.instance, ruleText);
        this.addAbility(conditionalAbility);
    }

    public MightMakesRight(final MightMakesRight card) {
        super(card);
    }

    @Override
    public MightMakesRight copy() {
        return new MightMakesRight(this);
    }
}

enum ControlsEachCreatureWithGreatestPowerCondition implements Condition {

    instance;

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    @Override
    public boolean apply(Game game, Ability source) {
        Integer maxPower = null;
        boolean result = false;
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent permanent : permanents) {
            if (permanent == null) {
                continue;
            }
            int power = permanent.getPower().getValue();
            if (maxPower == null || power > maxPower) {
                maxPower = permanent.getPower().getValue();
                result = true;
            }
            if (power == maxPower) {
                result &= permanent.getControllerId().equals(source.getControllerId());
            }
        }
        return result;
    }

}
