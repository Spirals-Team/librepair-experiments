
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class IgniteMemories extends CardImpl {

    public IgniteMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Target player reveals a card at random from their hand. Ignite Memories deals damage to that player equal to that card's converted mana cost.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new IgniteMemoriesEffect());
        // Storm
        this.addAbility(new StormAbility());
    }

    public IgniteMemories(final IgniteMemories card) {
        super(card);
    }

    @Override
    public IgniteMemories copy() {
        return new IgniteMemories(this);
    }
}

class IgniteMemoriesEffect extends OneShotEffect {

    public IgniteMemoriesEffect() {
        super(Outcome.Damage);
        staticText = "Target player reveals a card at random from their hand. {this} deals damage to that player equal to that card's converted mana cost";
    }

    public IgniteMemoriesEffect(final IgniteMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (!controller.getHand().isEmpty()) {
                Cards revealed = new CardsImpl();
                Card card = controller.getHand().getRandom(game);
                if (card != null) {
                    revealed.add(card);
                    controller.revealCards(sourceObject.getIdName(), revealed, game);
                    controller.damage(card.getConvertedManaCost(), source.getSourceId(), game, false, true);
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public IgniteMemoriesEffect copy() {
        return new IgniteMemoriesEffect(this);
    }

}
