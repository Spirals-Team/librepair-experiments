
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class StormWorld extends CardImpl {

    public StormWorld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        addSuperType(SuperType.WORLD);

        // At the beginning of each player's upkeep, Storm World deals X damage to that player, where X is 4 minus the number of cards in their hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new StormWorldEffect(), TargetController.ANY, false, true));

    }

    public StormWorld(final StormWorld card) {
        super(card);
    }

    @Override
    public StormWorld copy() {
        return new StormWorld(this);
    }
}

class StormWorldEffect extends OneShotEffect {

    public StormWorldEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals X damage to that player, where X is 4 minus the number of cards in their hand";
    }

    public StormWorldEffect(final StormWorldEffect effect) {
        super(effect);
    }

    @Override
    public StormWorldEffect copy() {
        return new StormWorldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int damage = 4 - player.getHand().size();
            if (damage > 0) {
                player.damage(damage, source.getSourceId(), game, false, true);
            }
            return true;
        }

        return false;
    }
}
