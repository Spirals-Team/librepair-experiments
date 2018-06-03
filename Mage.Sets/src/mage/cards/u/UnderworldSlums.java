
package mage.cards.u;

import mage.MageObject;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class UnderworldSlums extends CardImpl {

    public UnderworldSlums(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C} to your manapool.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Underworld Slums: Search your library for a basic Swamp, Mountain or Forest card and put it onto the battlefield tapped. Then shuffle your library.
        this.addAbility(new UnderworldSlumsAbility());

    }

    public UnderworldSlums(final UnderworldSlums card) {
        super(card);
    }

    @Override
    public UnderworldSlums copy() {
        return new UnderworldSlums(this);
    }

    public static class UnderworldSlumsAbility extends ActivatedAbilityImpl {

        public UnderworldSlumsAbility(UnderworldSlumsAbility ability) {
            super(ability);
        }

        public UnderworldSlumsAbility() {
            super(Zone.BATTLEFIELD, null);
            addCost(new TapSourceCost());
            addCost(new SacrificeSourceCost());
            FilterCard filter = new FilterCard("basic Swamp, Mountain or Forest");
            filter.add(new CardTypePredicate(CardType.LAND));
            List<Predicate<MageObject>> subtypePredicates = new ArrayList<>();
            subtypePredicates.add(new SubtypePredicate(SubType.SWAMP));
            subtypePredicates.add(new SubtypePredicate(SubType.MOUNTAIN));
            subtypePredicates.add(new SubtypePredicate(SubType.FOREST));
            filter.add(Predicates.or(subtypePredicates));
            filter.add(new SupertypePredicate(SuperType.BASIC));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            addEffect(new SearchLibraryPutInPlayEffect(target, true, true, Outcome.PutLandInPlay));
        }

        @Override
        public UnderworldSlumsAbility copy() {
            return new UnderworldSlumsAbility(this);
        }
    }
}
