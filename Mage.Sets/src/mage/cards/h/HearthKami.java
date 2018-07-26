
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetAdjustment;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public final class HearthKami extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact with converted mana cost X");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public HearthKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {X}, Sacrifice Hearth Kami: Destroy target artifact with converted mana cost X.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{X}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjustment(TargetAdjustment.X_CMC_EQUAL_PERM);
        this.addAbility(ability);
    }

    public HearthKami(final HearthKami card) {
        super(card);
    }

    @Override
    public HearthKami copy() {
        return new HearthKami(this);
    }

}
