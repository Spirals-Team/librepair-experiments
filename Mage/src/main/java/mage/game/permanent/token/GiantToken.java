

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class GiantToken extends TokenImpl {

    public GiantToken() {
        super("Giant", "4/4 red Giant creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GIANT);
        color.setRed(true);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public GiantToken(final GiantToken token) {
        super(token);
    }

    public GiantToken copy() {
        return new GiantToken(this);
    }
}
