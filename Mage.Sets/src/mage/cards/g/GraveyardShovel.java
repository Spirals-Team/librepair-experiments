
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public final class GraveyardShovel extends CardImpl {

    public GraveyardShovel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {2}, {tap}: Target player exiles a card from their graveyard. If it's a creature card, you gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GraveyardShovelEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public GraveyardShovel(final GraveyardShovel card) {
        super(card);
    }

    @Override
    public GraveyardShovel copy() {
        return new GraveyardShovel(this);
    }
}

class GraveyardShovelEffect extends OneShotEffect {

    public GraveyardShovelEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player exiles a card from their graveyard. If it's a creature card, you gain 2 life";
    }

    public GraveyardShovelEffect(final GraveyardShovelEffect effect) {
        super(effect);
    }

    @Override
    public GraveyardShovelEffect copy() {
        return new GraveyardShovelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controller != null) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard();
            if (targetPlayer.chooseTarget(Outcome.Exile, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    targetPlayer.getGraveyard().remove(card);
                    card.moveToExile(null, "", source.getSourceId(), game);
                    if (card.isCreature()) {
                        controller.gainLife(2, game, source);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
