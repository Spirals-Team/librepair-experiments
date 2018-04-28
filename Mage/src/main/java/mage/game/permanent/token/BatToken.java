package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public class BatToken extends TokenImpl {

    public BatToken() {
        super("Bat", "1/1 black Bat creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.BAT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());

        this.setOriginalExpansionSetCode("MMA");
    }
    public BatToken(final BatToken token) {
        super(token);
    }

    public BatToken copy() {
        return new BatToken(this);
    }
}
