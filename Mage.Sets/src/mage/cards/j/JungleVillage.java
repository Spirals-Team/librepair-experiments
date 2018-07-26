
package mage.cards.j;

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
public final class JungleVillage extends CardImpl {

    public JungleVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Add {C} to your manapool.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Jungle Village: Search your library for a basic Mountain, Forest or Plains card and put it onto the battlefield tapped. Then shuffle your library.
        this.addAbility(new JungleVillageAbility());

    }

    public JungleVillage(final JungleVillage card) {
        super(card);
    }

    @Override
    public JungleVillage copy() {
        return new JungleVillage(this);
    }

    public final class JungleVillageAbility extends ActivatedAbilityImpl {

        public JungleVillageAbility(JungleVillageAbility ability) {
            super(ability);
        }

        public JungleVillageAbility() {
            super(Zone.BATTLEFIELD, null);
            addCost(new TapSourceCost());
            addCost(new SacrificeSourceCost());
            FilterCard filter = new FilterCard("basic Mountain, Forest or Plains");
            filter.add(new CardTypePredicate(CardType.LAND));
            List<Predicate<MageObject>> subtypePredicates = new ArrayList<>();
            subtypePredicates.add(new SubtypePredicate(SubType.PLAINS));
            subtypePredicates.add(new SubtypePredicate(SubType.MOUNTAIN));
            subtypePredicates.add(new SubtypePredicate(SubType.FAERIE));
            filter.add(Predicates.or(subtypePredicates));
            filter.add(new SupertypePredicate(SuperType.BASIC));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            addEffect(new SearchLibraryPutInPlayEffect(target, true, true, Outcome.PutLandInPlay));
        }

        @Override
        public JungleVillageAbility copy() {
            return new JungleVillageAbility(this);
        }
    }
}
