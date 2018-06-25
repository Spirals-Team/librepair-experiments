
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasementControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class JadeLeech extends CardImpl {

    private static final FilterCard filter = new FilterCard("Green spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public JadeLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Green spells you cast cost {G} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new SpellsCostIncreasementControllerEffect(filter, new ManaCostsImpl("{G}"))));
    }

    public JadeLeech(final JadeLeech card) {
        super(card);
    }

    @Override
    public JadeLeech copy() {
        return new JadeLeech(this);
    }
}
