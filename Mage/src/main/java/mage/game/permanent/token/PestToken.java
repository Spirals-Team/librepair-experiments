

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class PestToken extends TokenImpl {

    public PestToken() {
        super("Pest", "0/1 colorless Pest artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PEST);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public PestToken(final PestToken token) {
        super(token);
    }

    public PestToken copy() {
        return new PestToken(this);
    }
}
