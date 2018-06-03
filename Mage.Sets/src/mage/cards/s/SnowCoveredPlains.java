
package mage.cards.s;

import java.util.UUID;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public final class SnowCoveredPlains extends CardImpl {

    public SnowCoveredPlains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        addSuperType(SuperType.BASIC);
        addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.PLAINS);

        // W
        this.addAbility(new WhiteManaAbility());
    }

    public SnowCoveredPlains(final SnowCoveredPlains card) {
        super(card);
    }

    @Override
    public SnowCoveredPlains copy() {
        return new SnowCoveredPlains(this);
    }
}
