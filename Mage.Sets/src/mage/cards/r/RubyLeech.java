
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasementControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class RubyLeech extends CardImpl {

    private static final FilterCard filter = new FilterCard("Red spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public RubyLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Red spells you cast cost {R} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new SpellsCostIncreasementControllerEffect(filter, new ManaCostsImpl("{R}"))));
    }

    public RubyLeech(final RubyLeech card) {
        super(card);
    }

    @Override
    public RubyLeech copy() {
        return new RubyLeech(this);
    }
}
