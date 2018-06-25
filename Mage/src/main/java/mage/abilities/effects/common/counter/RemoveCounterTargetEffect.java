
package mage.abilities.effects.common.counter;

import java.util.HashSet;
import java.util.Set;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class RemoveCounterTargetEffect extends OneShotEffect {

    private final Counter counter;

    public RemoveCounterTargetEffect() {
        super(Outcome.UnboostCreature);
        counter = null;
    }

    public RemoveCounterTargetEffect(Counter counter) {
        super(Outcome.UnboostCreature);
        this.counter = counter;
    }

    public RemoveCounterTargetEffect(RemoveCounterTargetEffect effect) {
        super(effect);
        this.counter = effect.counter == null ? null : effect.counter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        if (p != null) {
            Counter toRemove = (counter == null ? selectCounterType(game, source, p) : counter);
            if (toRemove != null && p.getCounters(game).getCount(toRemove.getName()) >= toRemove.getCount()) {
                p.removeCounters(toRemove.getName(), toRemove.getCount(), game);
                if (!game.isSimulation()) {
                    game.informPlayers("Removed " + toRemove.getCount() + ' ' + toRemove.getName()
                            + " counter from " + p.getName());
                }
            }
        } else {
            Card c = game.getCard(targetPointer.getFirst(game, source));
            if (c != null && counter != null && c.getCounters(game).getCount(counter.getName()) >= counter.getCount()) {
                c.removeCounters(counter.getName(), counter.getCount(), game);
                if (!game.isSimulation()) {
                    game.informPlayers(new StringBuilder("Removed ").append(counter.getCount()).append(' ').append(counter.getName())
                            .append(" counter from ").append(c.getName())
                            .append(" (").append(c.getCounters(game).getCount(counter.getName())).append(" left)").toString());
                }
            }
        }
        return true;
    }

    private Counter selectCounterType(Game game, Ability source, Permanent permanent) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !permanent.getCounters(game).isEmpty()) {
            String counterName = null;
            if (permanent.getCounters(game).size() > 1) {
                Choice choice = new ChoiceImpl(true);
                Set<String> choices = new HashSet<>();
                for (Counter counterOnPermanent : permanent.getCounters(game).values()) {
                    if (permanent.getCounters(game).getCount(counterOnPermanent.getName()) > 0) {
                        choices.add(counterOnPermanent.getName());
                    }
                }
                choice.setChoices(choices);
                choice.setMessage("Choose a counter type to remove from " + permanent.getName());
                if (controller.choose(Outcome.Detriment, choice, game)) {
                    counterName = choice.getChoice();
                } else {
                    return null;
                }
            } else {
                for (Counter counterOnPermanent : permanent.getCounters(game).values()) {
                    if (counterOnPermanent.getCount() > 0) {
                        counterName = counterOnPermanent.getName();
                    }
                }
            }
            return new Counter(counterName);
        }
        return null;
    }

    @Override
    public RemoveCounterTargetEffect copy() {
        return new RemoveCounterTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        String text = "remove ";
        if (counter == null) {
            text += "a counter";
        } else {
            text += CardUtil.numberToText(counter.getCount(), "a") + ' ' + counter.getName();
            text += counter.getCount() > 1 ? " counters" : " counter";
        }
        text += " from target " + (mode.getTargets().isEmpty() ? " object" : mode.getTargets().get(0).getTargetName());
        return text;
    }
}
