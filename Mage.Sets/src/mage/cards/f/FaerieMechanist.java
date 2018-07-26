

package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX
 */
public final class FaerieMechanist extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact card");
    static {
            filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public FaerieMechanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ARTIFICER);
    this.power = new MageInt(2);
    this.toughness = new MageInt(2);
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Faerie Mechanist enters the battlefield, look at the top three cards of your library. 
        // You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(new StaticValue(3), false, new StaticValue(1), filter, false)));
    }

    public FaerieMechanist(final FaerieMechanist card) {
        super(card);
    }

    @Override
    public FaerieMechanist copy() {
        return new FaerieMechanist(this);
    }

}
