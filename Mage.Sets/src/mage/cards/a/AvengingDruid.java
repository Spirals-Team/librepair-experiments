
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;

/**
 *
 * @author Styxo
 */
public final class AvengingDruid extends CardImpl {

    public AvengingDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Avenging Druid deals damage to an opponent, you may reveal cards from the top of your library until you reveal a land card. If you do, put that card onto the battlefield and put all other cards revealed this way into your graveyard.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new RevealCardsFromLibraryUntilEffect(new FilterLandCard(), Zone.BATTLEFIELD, Zone.GRAVEYARD), true));
    }

    public AvengingDruid(final AvengingDruid card) {
        super(card);
    }

    @Override
    public AvengingDruid copy() {
        return new AvengingDruid(this);
    }
}
