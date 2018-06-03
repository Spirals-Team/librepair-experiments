

package mage.abilities.effects.common.continuous;

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author ayratn
 */
public class SwitchPowerToughnessTargetEffect extends ContinuousEffectImpl {

    public SwitchPowerToughnessTargetEffect(Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, Outcome.BoostCreature);
    }

    public SwitchPowerToughnessTargetEffect(final SwitchPowerToughnessTargetEffect effect) {
        super(effect);
    }

    @Override
    public SwitchPowerToughnessTargetEffect copy() {
        return new SwitchPowerToughnessTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            int power = target.getPower().getValue();
            target.getPower().setValue(target.getToughness().getValue());
            target.getToughness().setValue(power);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Switch target ").append(mode.getTargets().get(0).getTargetName()).append("'s power and toughness")
                .append(' ').append(duration.toString());
        return sb.toString();
    }


}