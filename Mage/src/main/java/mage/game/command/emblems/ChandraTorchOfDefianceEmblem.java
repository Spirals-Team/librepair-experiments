
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.command.Emblem;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author spjspj
 */
public class ChandraTorchOfDefianceEmblem extends Emblem {
    
    // You get an emblem with "Whenever you cast a spell, this emblem deals 5 damage to any target."
    public ChandraTorchOfDefianceEmblem() {
        this.setName("Emblem Chandra");
        Effect effect = new DamageTargetEffect(5);
        effect.setText("this emblem deals 5 damage to any target");
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, effect, new FilterSpell("a spell"), false, false);
        ability.addTarget(new TargetAnyTarget());
        getAbilities().add(ability);
    }
}
