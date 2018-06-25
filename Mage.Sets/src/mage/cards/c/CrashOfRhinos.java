
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class CrashOfRhinos extends CardImpl {

    public CrashOfRhinos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.RHINO);

        this.power = new MageInt(8);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    public CrashOfRhinos(final CrashOfRhinos card) {
        super(card);
    }

    @Override
    public CrashOfRhinos copy() {
        return new CrashOfRhinos(this);
    }
}
