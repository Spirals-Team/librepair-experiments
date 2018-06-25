
package mage.cards.n;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.continuous.BecomesBlackZombieAdditionEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class NecromanticSelection extends CardImpl {

    public NecromanticSelection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}{B}");

        // Destroy all creatures, then return a creature card put into a graveyard this way to the battlefield under your control. It's a black Zombie in addition to its other colors and types. Exile Necromantic Selection.
        this.getSpellAbility().addEffect(new NecromanticSelectionEffect());
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());

    }

    public NecromanticSelection(final NecromanticSelection card) {
        super(card);
    }

    @Override
    public NecromanticSelection copy() {
        return new NecromanticSelection(this);
    }
}

class NecromanticSelectionEffect extends OneShotEffect {

    public NecromanticSelectionEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures, then return a creature card put into a graveyard this way to the battlefield under your control. It's a black Zombie in addition to its other colors and types";
    }

    public NecromanticSelectionEffect(final NecromanticSelectionEffect effect) {
        super(effect);
    }

    @Override
    public NecromanticSelectionEffect copy() {
        return new NecromanticSelectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), source.getSourceId(), game)) {
                permanent.destroy(source.getSourceId(), game, false);
                if (game.getState().getZone(permanent.getId()) == Zone.GRAVEYARD) {
                    cards.add(permanent);
                }
            }
            FilterCard filter = new FilterCreatureCard("creature card put into a graveyard with " + sourceObject.getLogName());
            List<Predicate<MageObject>> cardIdPredicates = new ArrayList<>();
            for (UUID cardId : cards) {
                cardIdPredicates.add(new CardIdPredicate(cardId));
            }
            filter.add(Predicates.or(cardIdPredicates));
            Target target = new TargetCardInGraveyard(filter);
            if (controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    ContinuousEffect effect = new BecomesBlackZombieAdditionEffect();
                    effect.setText("It's a black Zombie in addition to its other colors and types");
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
