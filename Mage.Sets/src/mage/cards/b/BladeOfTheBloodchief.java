
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author mamaurer, nantuko
 */
public final class BladeOfTheBloodchief extends CardImpl {

    public BladeOfTheBloodchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever a creature dies, put a +1/+1 counter on equipped creature. If equipped creature is a Vampire, put two +1/+1 counters on it instead.
        this.addAbility(new DiesCreatureTriggeredAbility(new BladeOfTheBloodchiefEffect(), false));
        
        //Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    public BladeOfTheBloodchief(final BladeOfTheBloodchief card) {
        super(card);
    }

    @Override
    public BladeOfTheBloodchief copy() {
        return new BladeOfTheBloodchief(this);
    }
}

class BladeOfTheBloodchiefEffect extends OneShotEffect {

    BladeOfTheBloodchiefEffect() {
        super(Outcome.BoostCreature);
    }

    BladeOfTheBloodchiefEffect(final BladeOfTheBloodchiefEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature != null) {
                if (creature.hasSubtype(SubType.VAMPIRE, game)) {
                    creature.addCounters(CounterType.P1P1.createInstance(2), source, game);
                } else {
                    creature.addCounters(CounterType.P1P1.createInstance(), source, game);
                }
            }
        }
        return true;
    }

    @Override
    public BladeOfTheBloodchiefEffect copy() {
        return new BladeOfTheBloodchiefEffect(this);
    }
}
