
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class GargoyleToken extends TokenImpl {

    public GargoyleToken() {
        super("Gargoyle", "3/4 colorless Gargoyle artifact creature token with flying");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.GARGOYLE);
        power = new MageInt(3);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    public GargoyleToken(final GargoyleToken token) {
        super(token);
    }

    public GargoyleToken copy() {
        return new GargoyleToken(this);
    }
}
