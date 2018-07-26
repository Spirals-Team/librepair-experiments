
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class EsperPanorama extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Plains, Island, or Swamp");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
        filter.add(new SupertypePredicate(SuperType.BASIC));
        filter.add(Predicates.or(
                new SubtypePredicate(SubType.PLAINS),
                new SubtypePredicate(SubType.ISLAND),
                new SubtypePredicate(SubType.SWAMP)));
    }

    public EsperPanorama(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new ColorlessManaAbility());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(target, true, Outcome.PutLandInPlay), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public EsperPanorama(final EsperPanorama card) {
        super(card);
    }

    @Override
    public EsperPanorama copy() {
        return new EsperPanorama(this);
    }
}
