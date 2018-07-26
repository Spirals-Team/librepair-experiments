
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author LevelX2
 */
public final class KarametrasAcolyte extends CardImpl {

    public KarametrasAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {T}: Add an amount of {G} equal to your devotion to green.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana(1), new DevotionCount(ColoredManaSymbol.G),
                "Add an amount of {G} equal to your devotion to green. (Each {G} in the mana costs of permanents you control counts towards your devotion to green.)"));
    }

    public KarametrasAcolyte(final KarametrasAcolyte card) {
        super(card);
    }

    @Override
    public KarametrasAcolyte copy() {
        return new KarametrasAcolyte(this);
    }
}
