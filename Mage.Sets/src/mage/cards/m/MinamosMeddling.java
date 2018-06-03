
package mage.cards.m;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;


/**
 *
 * @author LevelX2
 */
public final class MinamosMeddling extends CardImpl {

    public MinamosMeddling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");


        // Counter target spell. That spell's controller reveals their hand, then discards each card with the same name as a card spliced onto that spell.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL));
        this.getSpellAbility().addEffect(new MinamosMeddlingCounterTargetEffect());
    }

    public MinamosMeddling(final MinamosMeddling card) {
        super(card);
    }

    @Override
    public MinamosMeddling copy() {
        return new MinamosMeddling(this);
    }
}

class MinamosMeddlingCounterTargetEffect extends OneShotEffect {

    public MinamosMeddlingCounterTargetEffect() {
        super(Outcome.Benefit);
        staticText = "Counter target spell. That spell's controller reveals their hand, then discards each card with the same name as a card spliced onto that spell";
    }

    public MinamosMeddlingCounterTargetEffect(final MinamosMeddlingCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public MinamosMeddlingCounterTargetEffect copy() {
        return new MinamosMeddlingCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null) {
            for (UUID targetId : getTargetPointer().getTargets(game, source) ) {
                Spell spell = game.getStack().getSpell(targetId);
                if (spell != null) {
                    game.getStack().counter(targetId, source.getSourceId(), game);
                    Player spellController = game.getPlayer(spell.getControllerId());
                    if (spellController != null) {
                        spellController.revealCards(sourceObject.getName(), spellController.getHand(), game);
                        Cards cardsToDiscard = new CardsImpl();
                        for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                            if (spellAbility.getSpellAbilityType() == SpellAbilityType.SPLICE) {
                                for (Card card: spellController.getHand().getCards(game)) {
                                    if (card.getName().equals(spellAbility.getCardName())) {
                                        cardsToDiscard.add(card);
                                    }
                                }
                            }
                        }
                        if (!cardsToDiscard.isEmpty()) {
                            for (Card card :cardsToDiscard.getCards(game)) {
                                spellController.discard(card, source, game);
                            }
                        }
                    }

                }
            }
            return true;
        }
        return false;
    }

}
