
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class SyphonMind extends CardImpl {

    public SyphonMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");


        // Each other player discards a card. You draw a card for each card discarded this way.
        this.getSpellAbility().addEffect(new SyphonMindEffect());

    }

    public SyphonMind(final SyphonMind card) {
        super(card);
    }

    @Override
    public SyphonMind copy() {
        return new SyphonMind(this);
    }
}

class SyphonMindEffect extends OneShotEffect {

    public SyphonMindEffect() {
        super(Outcome.Discard);
        this.staticText = "Each other player discards a card. You draw a card for each card discarded this way";
    }

    public SyphonMindEffect(final SyphonMindEffect effect) {
        super(effect);
    }

    @Override
    public SyphonMindEffect copy() {
        return new SyphonMindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        boolean result = false;
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            for (UUID playerId : you.getInRange()) {
                if (!playerId.equals(source.getControllerId())) {
                    Player otherPlayer = game.getPlayer(playerId);
                    if (otherPlayer != null && !otherPlayer.getHand().isEmpty()) {
                        TargetCardInHand target = new TargetCardInHand();
                        if (otherPlayer.choose(Outcome.Discard, target, source.getSourceId(), game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                if (otherPlayer.discard(card, source, game)) {
                                    amount += 1;
                                    result = true;
                                    target.clearChosen();
                                }
                            }
                        }
                    }
                }
            }            
            you.drawCards(amount, game);
        }
        return result;
    }
}
