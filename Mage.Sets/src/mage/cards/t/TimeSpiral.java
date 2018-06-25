
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class TimeSpiral extends CardImpl {

    public TimeSpiral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}{U}");

        // Exile Time Spiral. Each player shuffles their graveyard and hand into their library, then draws seven cards. You untap up to six lands.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
        this.getSpellAbility().addEffect(new TimeSpiralEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new UntapLandsEffect(6));
    }

    public TimeSpiral(final TimeSpiral card) {
        super(card);
    }

    @Override
    public TimeSpiral copy() {
        return new TimeSpiral(this);
    }
}

class TimeSpiralEffect extends OneShotEffect {

    public TimeSpiralEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles their hand and graveyard into their library";
    }

    public TimeSpiralEffect(final TimeSpiralEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (Card card : player.getHand().getCards(game)) {
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
                for (Card card : player.getGraveyard().getCards(game)) {
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }

    @Override
    public TimeSpiralEffect copy() {
        return new TimeSpiralEffect(this);
    }

}
