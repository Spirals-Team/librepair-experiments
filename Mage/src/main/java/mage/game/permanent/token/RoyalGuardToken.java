

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.abilities.keyword.FirstStrikeAbility;

/**
 *
 * @author spjspj
 */
public final class RoyalGuardToken extends TokenImpl {

    public RoyalGuardToken() {
        super("Royal Guard", "2/2 red Soldier creature token with first strike named Royal Guard", 2, 2);
        this.setOriginalExpansionSetCode("SWS");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        addAbility(FirstStrikeAbility.getInstance());
        subtype.add(SubType.SOLDIER);
    }

    public RoyalGuardToken(final RoyalGuardToken token) {
        super(token);
    }

    public RoyalGuardToken copy() {
        return new RoyalGuardToken(this);
    }
}
