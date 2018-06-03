
package mage.abilities.effects.common.counter;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class AddPoisonCounterTargetEffect extends OneShotEffect {

    protected int amount;

    public AddPoisonCounterTargetEffect(int amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public AddPoisonCounterTargetEffect(final AddPoisonCounterTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public AddPoisonCounterTargetEffect copy() {
        return new AddPoisonCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.addCounters(CounterType.POISON.createInstance(amount), game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if(staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "Target " + mode.getTargets().get(0).getTargetName() + " gets " + Integer.toString(amount) + " poison counter(s).";
    }
}
