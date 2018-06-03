
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class SungrassEgg extends CardImpl {

    public SungrassEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {2}, {tap}, Sacrifice Sungrass Egg: Add {G}{W}. Draw a card.
        ActivatedManaAbilityImpl ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 0, 1, 0, 0, 0, 0), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    public SungrassEgg(final SungrassEgg card) {
        super(card);
    }

    @Override
    public SungrassEgg copy() {
        return new SungrassEgg(this);
    }
}
