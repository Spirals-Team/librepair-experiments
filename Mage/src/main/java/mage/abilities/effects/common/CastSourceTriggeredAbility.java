
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author Plopman
 */
public class CastSourceTriggeredAbility extends TriggeredAbilityImpl {

    public static final String SOURCE_CAST_SPELL_ABILITY = "sourceCastSpellAbility";

    public CastSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public CastSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.STACK, effect, optional);
        this.ruleAtTheTop = true;
    }

    public CastSourceTriggeredAbility(final CastSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CastSourceTriggeredAbility copy() {
        return new CastSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            MageObject spellObject = game.getObject(sourceId);
            if (spellObject != null && (spellObject instanceof Spell)) {
                Spell spell = (Spell) spellObject;
                if (spell.getSpellAbility() != null) {
                    for (Effect effect : getEffects()) {
                        effect.setValue(SOURCE_CAST_SPELL_ABILITY, spell.getSpellAbility());
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you cast {source}, " + super.getRule();
    }
}
