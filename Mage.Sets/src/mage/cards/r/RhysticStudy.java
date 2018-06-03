
package mage.cards.r;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public final class RhysticStudy extends CardImpl {

    public RhysticStudy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Whenever an opponent casts a spell, you may draw a card unless that player pays {1}.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new RhysticStudyDrawEffect(), StaticFilters.FILTER_SPELL, false, SetTargetPointer.PLAYER));
    }

    public RhysticStudy(final RhysticStudy card) {
        super(card);
    }

    @Override
    public RhysticStudy copy() {
        return new RhysticStudy(this);
    }
}

class RhysticStudyDrawEffect extends OneShotEffect {

    public RhysticStudyDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you may draw a card unless that player pays {1}";
    }
    
    public RhysticStudyDrawEffect(final RhysticStudyDrawEffect effect) {
        super(effect);
    }

    @Override
    public RhysticStudyDrawEffect copy() {
        return new RhysticStudyDrawEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && opponent != null && sourceObject != null) {
            Cost cost = new GenericManaCost(1);
            String message = "Would you like to pay {1} to prevent the opponent to draw a card?";
            if (!(opponent.chooseUse(Outcome.Benefit, message, source, game) && cost.pay(source, game, source.getSourceId(), opponent.getId(), false, null))) {
                if(controller.chooseUse(Outcome.DrawCard, "Draw a card (" + sourceObject.getLogName() + ')', source, game)) {
                    controller.drawCards(1, game);
                }
            }
            return true;
        }
        return false;
    }
    
}
