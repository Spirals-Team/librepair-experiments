
package mage.abilities.common.delayed;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author nantuko
 */
public class PactDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public PactDelayedTriggeredAbility(ManaCosts cost) {
        super(new PactEffect(cost));
    }


    public PactDelayedTriggeredAbility(PactDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PactDelayedTriggeredAbility copy() {
        return new PactDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getActivePlayerId().equals(this.getControllerId());
    }

    

    @Override
    public String getRule() {
        return "At the beginning of your next upkeep " + modes.getText();
    }
}

class PactEffect extends OneShotEffect {

    private ManaCosts cost;


    public PactEffect(ManaCosts cost) {
        super(Outcome.Neutral);
        this.cost = cost;
        staticText = "pay " + cost.getText() + ". If you don't, you lose the game";
    }

    public PactEffect(final PactEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public PactEffect copy() {
        return new PactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) { 
            if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText()  + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)){
                    return true;
                }
            }
            player.lost(game);
            return true;
        }
        return false;
    }

    

}
