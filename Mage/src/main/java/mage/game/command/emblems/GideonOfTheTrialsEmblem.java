
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;

/**
 *
 * @author spjspj
 */
public class GideonOfTheTrialsEmblem extends Emblem {

    public GideonOfTheTrialsEmblem() {
        this.setName("Emblem - Gideon");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new GideonOfTheTrialsCantLoseEffect());
        this.getAbilities().add(ability);
    }
}

class GideonOfTheTrialsCantLoseEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("a Gideon planeswalker");

    static {
        filter.add(new SubtypePredicate(SubType.GIDEON));
    }

    public GideonOfTheTrialsCantLoseEffect() {
        super(Duration.EndOfGame, Outcome.Benefit, false, false);
        staticText = "As long as you control a Gideon planeswalker, you can't lose the game and your opponents can't win the game";
    }

    public GideonOfTheTrialsCantLoseEffect(final GideonOfTheTrialsCantLoseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ((event.getType() == GameEvent.EventType.WINS && game.getOpponents(source.getControllerId()).contains(event.getPlayerId()))
                || (event.getType() == GameEvent.EventType.LOSES && event.getPlayerId().equals(source.getControllerId()))) {
            if (game.getBattlefield().contains(filter, source.getControllerId(), 1, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GideonOfTheTrialsCantLoseEffect copy() {
        return new GideonOfTheTrialsCantLoseEffect(this);
    }
}
