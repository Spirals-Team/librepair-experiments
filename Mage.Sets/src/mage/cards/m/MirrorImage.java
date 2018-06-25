package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class MirrorImage extends CardImpl {

    public MirrorImage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Mirror Image enter the battlefield as a copy of any creature you control.
        this.addAbility(new EntersBattlefieldAbility(
                new CopyPermanentEffect(StaticFilters.FILTER_CONTROLLED_CREATURE)
                        .setText("you may have {this} enter the battlefield "
                                + "as a copy of any creature you control"),
                true
        ));
    }

    public MirrorImage(final MirrorImage card) {
        super(card);
    }

    @Override
    public MirrorImage copy() {
        return new MirrorImage(this);
    }
}
