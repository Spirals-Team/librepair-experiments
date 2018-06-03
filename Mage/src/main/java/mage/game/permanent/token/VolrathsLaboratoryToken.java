

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.MageInt;
import mage.ObjectColor;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class VolrathsLaboratoryToken extends TokenImpl {

    public VolrathsLaboratoryToken() {
        this(null, null);
    }
    public VolrathsLaboratoryToken(ObjectColor color, SubType type) {
        super(type != null ? type.getDescription() : "", "2/2 creature token of the chosen color and type");
        cardType.add(CardType.CREATURE);
        if (color != null) {
            this.color.setColor(color);
        }
        if (type != null) {
            subtype.add(type);
        }
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public VolrathsLaboratoryToken(final VolrathsLaboratoryToken token) {
        super(token);
    }

    public VolrathsLaboratoryToken copy() {
        return new VolrathsLaboratoryToken(this);
    }
}
