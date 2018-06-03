
package mage.abilities.effects.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayTargetWithoutPayingManaEffect extends OneShotEffect {

    public PlayTargetWithoutPayingManaEffect() {
        super(Outcome.GainControl);
    }

    public PlayTargetWithoutPayingManaEffect(final PlayTargetWithoutPayingManaEffect effect) {
        super(effect);
    }

    @Override
    public PlayTargetWithoutPayingManaEffect copy() {
        return new PlayTargetWithoutPayingManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card target = (Card) game.getObject(source.getFirstTarget());
        if (controller != null && target != null) {
            return controller.cast(target.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (!mode.getTargets().isEmpty()) {
            Target target = mode.getTargets().get(0);
            if (mode.getTargets().get(0).getZone() == Zone.HAND) {
                sb.append("you may put ").append(target.getTargetName()).append(" from your hand onto the battlefield");
            } else {
                sb.append("you may cast target ").append(target.getTargetName()).append(" without paying its mana cost");
            }
        }
        return sb.toString();
    }
}
