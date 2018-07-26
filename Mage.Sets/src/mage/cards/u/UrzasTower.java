
package mage.cards.u;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.UrzaTerrainValue;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Melkhior
 */
public final class UrzasTower extends CardImpl {
    public UrzasTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.URZAS, SubType.TOWER);

        // {T}: Add {C}. If you control an Urza's Mine and an Urza's Power-Plant, add {C}{C}{C} instead.
        Ability urzaManaAbility = new DynamicManaAbility(Mana.ColorlessMana(1), new UrzaTerrainValue(3),
                "Add {C}. If you control an Urza's Mine and an Urza's Power-Plant, add {C}{C}{C} instead");
        this.addAbility(urzaManaAbility);
    }

    public UrzasTower(final UrzasTower card) {
        super(card);
    }

    @Override
    public UrzasTower copy() {
        return new UrzasTower(this);
    }
}
